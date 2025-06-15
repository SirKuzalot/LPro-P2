public class ASTString implements ASTNode {
    private final String value;

    public ASTString(String value) {
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return new VString(value);
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTString();
    }
}