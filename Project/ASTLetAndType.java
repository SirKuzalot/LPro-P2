import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        for (Bind p: types) {
            String id = p.getId();
            ASTType type = p.getType();
            try {
                newEnv.assoc(id, type);
            } catch (InterpreterError ex) {
                throw new TypeCheckError("Error associating identifier " + id + " with type " + type.toStr());
            }
        }

        for (Bind p : decls) {
            String id = p.getId();
            ASTNode exp = p.getExp();
            ASTType type = p.getType();

            if (type != null) {
                try {
                    type = type.simplify(newEnv);
                } catch (InterpreterError ie) {
                    System.out.println("Blast\n");

                    throw new TypeCheckError("Type " + type.toStr() + " not found in environment");
                }
                try {
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