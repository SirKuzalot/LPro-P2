public class ASTVariant implements ASTNode {
    private String label;
    private ASTNode value;

    public ASTVariant(String label0, ASTNode value0) {
        label = label0;
        value = value0;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v = value.eval(env);
        return new VVariant(label, v);
    }
}