import java.util.List;

public class ASTFun implements ASTNode {
    String parameter;
    ASTNode body;

    public ASTFun(String parameter0, ASTNode body0) {
        parameter = parameter0;
        body = body0;
    }

    public void setBody(ASTNode body0) {
        body = body0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        return new VFun(parameter, body, e);
    }
    
}
