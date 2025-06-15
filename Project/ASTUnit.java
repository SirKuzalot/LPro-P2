public class ASTUnit implements ASTNode {
    public ASTUnit() {}

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return new VUnit();
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTUnit();
    }
}