import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class ASTLetAndType implements ASTNode {
    private List<Bind> decls;
    private List<Bind> types;
    private ASTNode body;

    public ASTLetAndType(List<Bind> decls, List<Bind> types, ASTNode body) {
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

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        Environment<ASTType> newEnv = e.beginScope();
        for (Bind p : types) {
            String id = p.getId();
            try {
                newEnv.assoc(id, p.getType()); 
            } catch (InterpreterError ex) {
                throw new TypeCheckError("Error associating identifier " + id + " with type " + new ASTTId(id).toStr());
            }
        }

        for (Bind p : decls) {
            String id = p.getId();
            ASTNode exp = p.getExp();
            ASTType type = p.getType();

            if (type != null) {
                try {
                    while (type instanceof ASTTId) {
                        type = newEnv.find(type.toStr());
                    }
                    newEnv.assoc(id, type);
                } catch (InterpreterError ex) {
                    throw new TypeCheckError("Error associating identifier " + id + " with type " + type.toStr());
                }

            }

            ASTType expType = exp.typecheck(newEnv);

            if (type == null) {
                try {
                    newEnv.assoc(id, expType);
                } catch (InterpreterError ex) {
                    throw new TypeCheckError("Error associating identifier " + id + " with type " + expType.toStr());
                }
            } else {
                if (!(expType.isSubtypeOf(type, newEnv))) {
                    throw new TypeCheckError("Type mismatch for identifier " + id + ": expected " + type.toStr() + ", but got " + expType.toStr());
                }
            }
        }

        ASTType bodyType = body.typecheck(newEnv);
        newEnv.endScope();

        return bodyType;
    }
}