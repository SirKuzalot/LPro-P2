public class ASTSeq implements ASTNode {
    ASTNode n1;
    ASTNode n2;

    ASTSeq(ASTNode n1, ASTNode n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        n1.eval(e);
        return n2.eval(e);
    }
    
}
