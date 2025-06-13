public class ASTFalse implements ASTNode  {

    public ASTFalse() {
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        // Returns a VBool object representing the boolean value false.
        return new VBool(false);                
    }
    
}
