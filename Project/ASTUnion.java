import java.util.HashMap;
import java.util.Map;

public class ASTUnion implements ASTNode {
    private String label;
    private ASTNode value;

    public ASTUnion(String label0, ASTNode value0) {
        label = label0;
        value = value0;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v = value.eval(env);
        return new VUnion(label, v);
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType valueType = value.typecheck(env);

        return new ASTTUnion(new TypeBindList(
            new HashMap<String, ASTType>(Map.of(label, valueType))));
    }
}