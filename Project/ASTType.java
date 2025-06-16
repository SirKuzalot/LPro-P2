import java.util.Set;

public interface ASTType  {
    String toStr();

    boolean isSubtypeOf(ASTType other, Environment<ASTType> e);

    ASTType simplify (Environment<ASTType> e, Set<String> visited) throws InterpreterError;
}


