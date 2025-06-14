public class ASTSeqExp implements ASTNode {
    ASTNode n1;
    ASTNode n2;

    ASTSeqExp(ASTNode n1, ASTNode n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v1 = n1.eval(e);

        if (v1 instanceof VBox) {
            IValue v = n2.eval(e);
            ((VBox)v1).setval(v);
            return v1; 
        } else {
            throw new InterpreterError("Attempting to set a non-box value");
        }
    }
    
}
