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
}