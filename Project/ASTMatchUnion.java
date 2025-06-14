import java.util.*;

public class ASTMatchUnion implements ASTNode {
    private ASTNode expr;
    private List<String> labels;
    private List<String> vars;
    private List<ASTNode> bodies;

    public ASTMatchUnion(ASTNode expr, List<String> labels, List<String> vars, List<ASTNode> bodies) {
        this.expr = expr;
        this.labels = labels;
        this.vars = vars;
        this.bodies = bodies;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v = expr.eval(env);
        if (!(v instanceof VUnion)) {
            throw new InterpreterError("Match on non-variant value");
        }
        VUnion vv = (VUnion) v;
        String lbl = vv.getLabel();
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals(lbl)) {
                Environment<IValue> newEnv = new Environment<>(env);
                newEnv.assoc(vars.get(i), vv.getValue());
                return bodies.get(i).eval(newEnv);
            }
        }
        throw new InterpreterError("No matching case for label: " + lbl);
    }
}