public class ASTComp implements ASTNode {
    ASTNode left;
    ASTNode right;
    String op;

    public ASTComp(ASTNode left0, String op0, ASTNode right0) {
        left = left0;
        op = op0;
        right = right0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue l = left.eval(e);
        IValue r = right.eval(e);

        if (l instanceof VInt && r instanceof VInt) {
            int lv = ((VInt) l).getval();
            int rv = ((VInt) r).getval();
            switch (op) {
                case "<":
                    return new VBool(lv < rv);
                case "<=":
                    return new VBool(lv <= rv);
                case ">":
                    return new VBool(lv > rv);
                case ">=":
                    return new VBool(lv >= rv);
                case "==":
                    return new VBool(lv == rv);
                case "~=":
                    return new VBool(lv != rv);
                default:
                    throw new InterpreterError("Unknown operator: " + op);
            }
        } else {
            throw new InterpreterError("Invalid types for comparison");
        }
    }
    
}
