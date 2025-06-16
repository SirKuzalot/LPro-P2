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

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        ASTType t1 = n1.typecheck(e);


        try {
            while (t1 instanceof ASTTId) {
                t1 = e.find(t1.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Error resolving type: " + ex.getMessage());
        }

        if (!(t1 instanceof ASTTRef)) {
            throw new TypeCheckError("Expected a box type, but got " + t1.toStr());
        }

        ASTTRef boxType = (ASTTRef) t1;
        return boxType.getType();
    }
    
}
