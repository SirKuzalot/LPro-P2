public	class ASTId implements ASTNode	{	

    String id;	
    
    public ASTId(String id)	{
        this.id = id;
    }

    public IValue eval(Environment<IValue> env)	throws
    InterpreterError {
    return env.find(id);	
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError { 
        try {
            ASTType type = e.find(id); 
            while (type instanceof ASTTId) {
                try {
                    type = e.find(((ASTTId) type).toStr());
                } catch (InterpreterError ex) {
                    throw new TypeCheckError("Type variable not found: " + type.toStr());
                }
            }
            return type; 
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Identifier not found: " + id);
        }
    }
}	
