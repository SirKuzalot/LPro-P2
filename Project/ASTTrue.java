public class ASTTrue implements ASTNode  {

    public ASTTrue() {
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        // Returns a new VBool object with the value true.
        return new VBool(true);                
    }
    
}
