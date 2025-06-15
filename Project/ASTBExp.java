public class ASTBExp implements ASTNode {
    ASTNode left;
    ASTNode right;
    String op;

    public ASTBExp(ASTNode l, String o, ASTNode r) {
        left = l;
        op = o;
        right = r;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue lv = left.eval(e);
        IValue rv = right.eval(e);

        boolean l = ((VBool) lv).getval();
        boolean r = ((VBool) rv).getval();
        switch (op) {
            case "&&":
                return new VBool(l && r);
            case "||":
                return new VBool(l || r);
            default:
                throw new InterpreterError("Unknown operator: " + op);
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType lt = left.typecheck(e);
        ASTType rt = right.typecheck(e);

        if (!(lt instanceof ASTTBool)) {
            throw new TypeCheckError("Left operand must be of type bool, but got " + lt.toStr());
        }
        if (!(rt instanceof ASTTBool)) {
            throw new TypeCheckError("Right operand must be of type bool, but got " + rt.toStr());
        }

        return new ASTTBool(); 
    }


    
}
