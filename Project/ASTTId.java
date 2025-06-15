public	class ASTTId implements ASTType	{	

    String id;	
    
    public ASTTId(String id)	{
        this.id = id;
    }
    
    public String toStr() {
        return id;
    }
    
    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {

        try {
            ASTType type = e.find(id);
            return type.isSubtypeOf(other, e);
        } catch (InterpreterError ie) {
            return false;
        }
    }

    public ASTType simplify(Environment<ASTType> e) throws InterpreterError {
        try {
            ASTType type = this;
            while (type instanceof ASTTId) {
                type = e.find(((ASTTId) type).id);
            }
            return type.simplify(e);
        } catch (InterpreterError ie) {
            throw new InterpreterError("Type " + id + " not found in environment");
        }
    }

}	
