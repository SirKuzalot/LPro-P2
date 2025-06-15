public class ASTBox implements ASTNode {
    ASTNode n1;

    public ASTBox(ASTNode n1) {
        this.n1 = n1;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v = n1.eval(e);
        return new VBox(v);
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        ASTType t = n1.typecheck(e);
        return new ASTTRef(t);
    }

    
}
