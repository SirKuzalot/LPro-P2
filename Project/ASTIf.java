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
        if (b.getval()) {
            return thenn.eval(e);
        } else {
            return elsee.eval(e);
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType tcond = cond.typecheck(e);

        try {
            while (tcond instanceof ASTTId) {
                tcond = e.find(((ASTTId) tcond).toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type variable not found: " + tcond.toStr());
        }

        if (!(tcond instanceof ASTTBool)) {
            throw new TypeCheckError("Condition of if must be a boolean, found " + tcond.toStr());
        }
        
        ASTType tthen = thenn.typecheck(e);
        ASTType telse = elsee.typecheck(e);

        if (tthen.isSubtypeOf(telse, e)) {
            return telse;
        } else if (telse.isSubtypeOf(tthen, e)) {
            return tthen;
        } else {
            throw new TypeCheckError("Branches of if must have compatible types: " + tthen.toStr() + " and " + telse.toStr());
        }
    }
    
}
