import java.util.Set;

class ASTTBool implements ASTType {
        
    public ASTTBool() {
    }
    public String toStr() {
        return "bool";
    }
    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        return other instanceof ASTTBool;
    }
}