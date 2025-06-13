public class ASTApp implements ASTNode {
    ASTNode n1;
    ASTNode n2;

    public ASTApp(ASTNode n1, ASTNode n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v1 = n1.eval(e);
        IValue v2 = n2.eval(e);

        if (v1 instanceof VFun) {
            return ((VFun)v1).apply(v2);
        } else {
            throw new InterpreterError("Not a function");
        }
    }
    
}
