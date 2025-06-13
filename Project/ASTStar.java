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
            if (v instanceof VInt) {
                return new VInt(((VInt) v).getval());
            } else if (v instanceof VBool) {
                return new VBool(((VBool) v).getval());
            } else if (v instanceof VFun) {
                return (VFun) v;
            } else if (v instanceof VBox) {
                return (VBox) v;
            } else if (v instanceof VCons) {
                return (VCons) v;
            } else if (v instanceof VLazyCons) {
                return (VLazyCons) v;
            }else {
                throw new InterpreterError("Attempting to unbox an invalid value");
            }
        } else {
            throw new InterpreterError("Unboxing only works for boxed values");
        }
    }
    
}
