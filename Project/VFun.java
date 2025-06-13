import java.util.List;

public class VFun implements IValue {
    List<String> parameters;
    ASTNode body;
    Environment<IValue> env;

    public VFun(List<String> parameters0, ASTNode body0, Environment<IValue> env0) {
        parameters = parameters0;
        body = body0;
        env = env0;
    }
    
    public IValue apply(IValue args) throws InterpreterError
    {

        Environment<IValue> newEnv = env.beginScope();

        newEnv.assoc(parameters.get(0), args);

        if (parameters.size() > 1) {
            return new VFun(parameters.subList(1, parameters.size()), body, newEnv);
        }

        return body.eval(newEnv);
    }

    public String toStr() {
        return "VFun{" +
                "parameters=" + parameters +
                ", body=" + body +
                ", env=" + env +
                '}';
    }
}
