import java.util.List;

public class VFun implements IValue {
    String parameter;
    ASTNode body;
    Environment<IValue> env;

    public VFun(String parameter0, ASTNode body0, Environment<IValue> env0) {
        parameter = parameter0;
        body = body0;
        env = env0;
    }
    
    public IValue apply(IValue args) throws InterpreterError
    {

        Environment<IValue> newEnv = env.beginScope();

        newEnv.assoc(parameter, args);

        return body.eval(newEnv);
    }

    public String toStr() {
        return "VFun{" +
                "parameters=" + parameter +
                ", body=" + body +
                ", env=" + env +
                '}';
    }
}
