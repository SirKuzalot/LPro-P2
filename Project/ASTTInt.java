public class ASTTInt implements ASTType {
    
    public String toStr() {
        return "int";
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        return other instanceof ASTTInt;
    }

    public ASTType simplify(Environment<ASTType> e) throws InterpreterError {
        return this; 
    }
}


