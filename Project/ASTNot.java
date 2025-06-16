public class ASTNot implements ASTNode  {

    private ASTNode n;
    
    public ASTNot(ASTNode n0) {
        n = n0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        IValue v = n.eval(e);
        return new VBool(!((VBool)v).getval());
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        ASTType t = n.typecheck(e);

        try {
            while (t instanceof ASTTId) {
                t = e.find(t.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Error resolving type: " + ex.getMessage());
        }
        
        if (!(t instanceof ASTTBool)) {
            throw new TypeCheckError("Not operator requires a boolean operand, found: " + t.toStr());
        }
        return new ASTTBool(); 
    }
    
}
