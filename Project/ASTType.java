public interface ASTType  {
    String toStr();

    boolean isSubtypeOf(ASTType other, Environment<ASTType> e);

    ASTType simplify (Environment<ASTType> e) throws InterpreterError;
}


