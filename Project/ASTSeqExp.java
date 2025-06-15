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

        IValue v = n2.eval(e);
        ((VBox)v1).setval(v);
        return v1; 

    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        ASTType t1 = n1.typecheck(e);
        ASTType t2 = n2.typecheck(e);

        if (!(t1 instanceof ASTTRef)) {
            throw new TypeCheckError("Expected a box type, but got " + t1);
        }


        ASTTRef boxType = (ASTTRef) t1;

        if (!(t2.isSubtypeOf(boxType.getType(), e))) {
            throw new TypeCheckError("Value type " + t2.toStr() + " is not a subtype of " + boxType.getType().toStr());
        }

        return t2;
    }
    
}
