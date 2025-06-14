import java.util.HashMap;
import java.util.Map;

public class ASTRecord implements ASTNode {
    private HashMap<String, ASTNode> fields;

    public ASTRecord(HashMap<String, ASTNode> fields0) {
        fields = fields0;
    }

    public HashMap<String, ASTNode> getFields() {
        return fields;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        HashMap<String, IValue> evaluatedFields = new HashMap<>();
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            evaluatedFields.put(entry.getKey(), entry.getValue().eval(env));
        }
        return new VRecord(evaluatedFields);
    }
}