public class ASTNot implements ASTNode  {

    private ASTNode n;
    
    public ASTNot(ASTNode n0) {
        n = n0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        // Evaluates the expression and returns a VBool object representing the negation of the boolean value.
        IValue v = n.eval(e);
        if (v instanceof VBool) {
            return new VBool(!((VBool)v).getval());
        } else {
            throw new InterpreterError("Not operator applied to non-boolean value");
        }
    }
    
}
