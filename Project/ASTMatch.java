public class ASTMatch implements ASTNode {
    ASTNode expr;
    ASTNode nilCase;
    String headId, tailId;
    ASTNode consCase;
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headId, String tailId, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headId = headId;
        this.tailId = tailId;
        this.consCase = consCase;
    }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        if (v instanceof VNil) {
            return nilCase.eval(e);
        } else if (v instanceof VCons) {
            VCons cons = (VCons) v;
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, cons.getHead());
            env2.assoc(tailId, cons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else if (v instanceof VLazyCons) {
            VLazyCons lazyCons = (VLazyCons) v;
            lazyCons.force();
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, lazyCons.getHead());
            env2.assoc(tailId, lazyCons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else {
            throw new InterpreterError("Match on non-list value");
        }
    }
}