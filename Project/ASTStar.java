public class ASTStar implements ASTNode {
    ASTNode n1;

    ASTStar(ASTNode n1) {
        this.n1 = n1;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v1 = n1.eval(e);
        if (v1 instanceof VBox) {
            IValue v = ((VBox) v1).getval();
            return v;
        } else {
            throw new InterpreterError("Unboxing only works for boxed values");
        }
    }
    
}
