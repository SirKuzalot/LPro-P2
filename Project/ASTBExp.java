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

        if (lv instanceof VBool && rv instanceof VBool) {
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
        } else {
            throw new InterpreterError("Type mismatch in binary expression");
        }
    }
    
}
