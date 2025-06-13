public class ASTIf implements ASTNode {
    ASTNode cond;
    ASTNode thenn;
    ASTNode elsee;

    public ASTIf(ASTNode cond, ASTNode thenn, ASTNode elsee) {
        this.cond = cond;
        this.thenn = thenn;
        this.elsee = elsee;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        VBool b = (VBool) cond.eval(e);
        if (!(b instanceof VBool)) {
            throw new InterpreterError("Condition must evaluate to a boolean");
        }
        if (b.getval()) {
            return thenn.eval(e);
        } else {
            return elsee.eval(e);
        }
    }
    
}
