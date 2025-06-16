public class ASTNeg implements ASTNode {

    ASTNode exp;

    public IValue eval(Environment <IValue>e) throws InterpreterError { 
	IValue v0 = exp.eval(e); 
	if (v0 instanceof VInt) { 
	    return new VInt(-((VInt)v0).getval()); 
	} else { 
	    throw new InterpreterError("illegal types to neg operator"); 
	}
    }
        
    public ASTNeg(ASTNode e)
    {
	exp = e;
    }

	public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
		ASTType t = exp.typecheck(e);

		try {
			while (t instanceof ASTTId) {
				t = e.find(t.toStr());
			}
		} catch (InterpreterError ex) {
			throw new TypeCheckError("Error resolving type: " + ex.getMessage());
		}
		
		if (!(t instanceof ASTTInt)) {
			throw new TypeCheckError("neg operator requires an int, found " + t.toStr());
		}
		return new ASTTInt();
	}

}

