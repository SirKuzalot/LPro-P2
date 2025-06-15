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

        return ((VFun)v1).apply(v2);

    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        ASTType t1 = n1.typecheck(e);
        ASTType t2 = n2.typecheck(e);

        if (!(t1 instanceof ASTTArrow)) {
            throw new TypeCheckError("Expected a function type, but got " + t1);
        }

        ASTTArrow funType = (ASTTArrow) t1;
        ASTType argType = funType.getDom();

        if (!(t2.isSubtypeOf(argType, e))) {
            throw new TypeCheckError("Argument type " + t2.toStr() + " is not a subtype of " + argType.toStr());
        }

        return funType.getCodom();

        
    }
    
}
