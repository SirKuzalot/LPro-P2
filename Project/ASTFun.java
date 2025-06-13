import java.util.List;

public class ASTFun implements ASTNode {
    List<String> parameters;
    ASTNode body;

    public ASTFun(List<String> parameters0, ASTNode body0) {
        parameters = parameters0;
        body = body0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        return new VFun(parameters, body, e);
    }
    
}
