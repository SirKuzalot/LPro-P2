import java.util.HashSet;

public class ASTFun implements ASTNode {
    String parameter;
    ASTNode body;
    ASTType type;

    public ASTFun(String parameter0, ASTNode body0, ASTType type0) {
        parameter = parameter0;
        body = body0;
        type = type0;
    }

    public void setBody(ASTNode body0) {
        body = body0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError
    {
        return new VFun(parameter, body, e);
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError
    {
        Environment<ASTType> newEnv = e.beginScope();
        
        try {
            newEnv.assoc(parameter, type);
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Error associating parameter " + parameter + " with type " + type.toStr());
        }

        ASTType bodyType = body.typecheck(newEnv);
        
        return new ASTTArrow(type, bodyType);
    }
    
}
