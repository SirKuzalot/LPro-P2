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
    
}
