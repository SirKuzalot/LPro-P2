public class ASTWhile implements ASTNode {
    ASTNode cond;
    ASTNode body;

    public ASTWhile(ASTNode cond, ASTNode body) {
        this.cond = cond;
        this.body = body;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        while (((VBool) cond.eval(e)).getval()) {
            body.eval(e);
        }
        return null;
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType condType = cond.typecheck(e);

        try {
            while (condType instanceof ASTTId) {
                condType = e.find(condType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type variable not found: " + condType.toStr());
        }
        
        if (!(condType instanceof ASTTBool)) {
            throw new TypeCheckError("While condition must be a boolean, found: " + condType.toStr());
        }
        body.typecheck(e); 
        return new ASTTUnit(); 
    }
    
}
