import java.util.HashMap;
import java.util.Map;

public class ASTStruct implements ASTNode {
    private HashMap<String, ASTNode> fields;

    public ASTStruct(HashMap<String, ASTNode> fields0) {
        fields = fields0;
    }

    public HashMap<String, ASTNode> getFields() {
        return fields;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        HashMap<String, IValue> evaluatedFields = new HashMap<>();
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            evaluatedFields.put(entry.getKey(), entry.getValue().eval(env));
        }
        return new VStruct(evaluatedFields);
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        HashMap<String, ASTType> fieldTypes = new HashMap<>();
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            ASTNode fieldNode = entry.getValue();
            ASTType fieldType = fieldNode.typecheck(env);
            fieldTypes.put(fieldName, fieldType);
        }
        return new ASTTStruct(new TypeBindList(fieldTypes));
    }
}