import java.util.Set;

public class ASTTString implements ASTType {

    public ASTTString() {}

    public String toStr() {
        return "string";
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        return other instanceof ASTTString;
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {
        return this; 
    }

}
