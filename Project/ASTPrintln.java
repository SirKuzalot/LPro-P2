public class ASTPrintln implements ASTNode {
    ASTNode n;
    
    ASTPrintln(ASTNode n) {
        this.n = n;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v = n.eval(e);
        System.out.println(v.toStr());
        return v;
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        return n.typecheck(e);
    }

}
