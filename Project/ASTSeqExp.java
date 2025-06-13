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

        if (v1 instanceof VBox) {
            IValue v = n2.eval(e);
            if (v instanceof VInt) {
                ((VBox)v1).setval((VInt) v);
                return v;
            } else if (v instanceof VBool) {
                ((VBox)v1).setval((VBool) v);
                return v;
            } else if (v instanceof VFun) {
                ((VBox)v1).setval((VFun) v);
                return v;
            } else if (v instanceof VBox) {
                ((VBox)v1).setval((VBox) v);
                return v;
            } else if (v instanceof VCons) {
                ((VBox)v1).setval((VCons) v);
                return v;
            } else if (v instanceof VLazyCons) {
                ((VBox)v1).setval((VLazyCons) v);
                return v;
            } else {
                throw new InterpreterError("Attempting to set an invalid value in a box");
            }
        } else {
            throw new InterpreterError("Attempting to set a non-box value");
        }
    }
    
}
