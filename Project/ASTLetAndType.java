import java.util.List;
import java.util.HashMap;

public class ASTLetAndType implements ASTNode {
    private List<Bind> decls;
    private HashMap<String, ASTType> types;
    private ASTNode body;

    public ASTLetAndType(List<Bind> decls, HashMap<String, ASTType> types, ASTNode body) {
        this.decls = decls;
        this.types = types;
        this.body = body;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        Environment<IValue> en = env.beginScope();
        for (Bind p : decls) {
            String id = p.getId();
            ASTNode exp = p.getExp();
            en.assoc(id, exp.eval(en));
        }
        IValue v = body.eval(en);
        en.endScope();
        return v;
    }
}