public class ASTFalse implements ASTNode  {

    public ASTFalse() {
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        return new VBool(false);                
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        return new ASTTBool(); 
    }
    
}
