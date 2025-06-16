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

}	
