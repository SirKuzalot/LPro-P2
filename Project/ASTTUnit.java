import java.util.Set;

class ASTTUnit implements ASTType {
        
    public ASTTUnit() {
    }
    public String toStr() {
        return "()";
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        return other instanceof ASTTUnit;
    }

    public ASTType simplify(Environment<ASTType> e) throws InterpreterError {
        return this; 
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {
        return this; 
    }
}