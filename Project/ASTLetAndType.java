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
                newEnv.assoc(id, new ASTTId(id)); 
            } catch (InterpreterError ex) {
                throw new TypeCheckError("Error associating identifier " + id + " with type " + new ASTTId(id).toStr());
            }
        }
        
        for (Bind p : types) {
            String id = p.getId();
            try {
                System.out.println("Type of " + id + " is " + p.getType().toStr());
                ASTType realType = p.getType().simplify(newEnv, new HashSet<String>(Collections.singleton(id)));
                System.out.println("Simplified type of " + id + " is " + realType.toStr()); 
                newEnv.assoc(id, realType);
            } catch (InterpreterError ex) {
                throw new TypeCheckError("Error associating identifier " + id + " with type " + p.getType().toStr());
            }
        }

        for (Bind p : decls) {
            String id = p.getId();
            ASTNode exp = p.getExp();
            ASTType type = p.getType();

            if (type != null) {
                try {
                    System.out.println("Type of " + id + " is " + type.toStr());
                    type = type.simplify(newEnv, new HashSet<>());
                    System.out.println("Simplified type of " + id + " is " + type.toStr());
                } catch (InterpreterError ie) {

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