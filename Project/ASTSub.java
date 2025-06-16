public class ASTSub implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	IValue v1 = lhs.eval(e);
	IValue v2 = rhs.eval(e);
	if (v1 instanceof VInt && v2 instanceof VInt) {
	    return new VInt(((VInt) v1).getval() - ((VInt) v2).getval());
	} else {
	    throw new InterpreterError("illegal types to + operator");
	}
    }

    public ASTSub(ASTNode l, ASTNode r) {
	lhs = l;
	rhs = r;
    }

	public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
		ASTType t1 = lhs.typecheck(e);
		ASTType t2 = rhs.typecheck(e);

		try {
			while (t1 instanceof ASTTId) {
				t1 = e.find(t1.toStr());
			}
			while (t2 instanceof ASTTId) {
				t2 = e.find(t2.toStr());
			}
		} catch (InterpreterError ex) {
			throw new TypeCheckError("Error resolving type: " + ex.getMessage());
		}

		
		if (!(t1 instanceof ASTTInt)) {
			throw new TypeCheckError("Left operand of - must be an int, found: " + t1.toStr());
		}
		if (!(t2 instanceof ASTTInt)) {
			throw new TypeCheckError("Right operand of - must be an int, found: " + t2.toStr());
		}
		return new ASTTInt();
	}

}
