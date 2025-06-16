import java.util.Set;

public class ASTTRef implements ASTType {

    private ASTType type;

    public ASTTRef(ASTType type) {
        this.type = type;
    }
    
    public ASTType getType() {
        return type;
    }
    public String toStr() {
        return "ref<"+type.toStr()+">";
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        if (other instanceof ASTTRef) {
            ASTTRef otherRef = (ASTTRef) other;
            return type.isSubtypeOf(otherRef.getType(), e) && 
                   otherRef.getType().isSubtypeOf(type, e);
        } else if (other instanceof ASTTId) {
            ASTTId otherId = (ASTTId) other;
            try {
                other = e.find(otherId.toStr());
            } catch (InterpreterError ex) {
                return false;
            }
            return this.isSubtypeOf(other, e);
        }
        return false;
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {
        
        type = type.simplify(e, visited);
        return this;

    }

}