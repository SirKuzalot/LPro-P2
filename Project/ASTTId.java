import java.util.Set;

public	class ASTTId implements ASTType	{	

    String id;	
    
    public ASTTId(String id)	{
        this.id = id;
    }
    
    public String toStr() {
        return id;
    }
    
    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {

        if (other.toStr().equals(id)) {
            return true;
        }
        try {
            ASTType type = e.find(id);
            return type.isSubtypeOf(other, e);
        } catch (InterpreterError ie) {
            return false;
        }
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {
        


        if (visited.contains(id)) {
            throw new InterpreterError("Circular type reference detected for " + id);
        }
        visited.add(id);

        ASTType type = null;
        try {
            type = e.find(((ASTTId) this).id);
        } catch (InterpreterError ie) {
            throw new InterpreterError("Type " + id + " not found in environment");
        }

        try {
            type = type.simplify(e, visited);
            return type;
        } catch (InterpreterError ie) {
            return this;
        }
    }

}	
