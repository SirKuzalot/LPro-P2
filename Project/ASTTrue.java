public class ASTTrue implements ASTNode  {

    public ASTTrue() {
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        return new VBool(true);                
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        return new ASTTBool(); 
    }

    
    
}
