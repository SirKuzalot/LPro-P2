public class ASTDiv implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	IValue v1 = lhs.eval(e);
	IValue v2 = rhs.eval(e);
	if (v1 instanceof VInt && v2 instanceof VInt) {
	    int i1 = ((VInt) v1).getval();
	    int i2 = ((VInt) v2).getval();
	    return new VInt(i1 / i2);
	} else {
	    throw new InterpreterError("illegal types to / operator");
	}
    }

    public ASTDiv(ASTNode l, ASTNode r) {
	lhs = l;
	rhs = r;
    }

	public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
		ASTType t1 = lhs.typecheck(e);
		ASTType t2 = rhs.typecheck(e);

		if (!(t1 instanceof ASTTInt)) {
			throw new TypeCheckError("Left operand must be of type int, but got " + t1.toStr());
		}
		if (!(t2 instanceof ASTTInt)) {
			throw new TypeCheckError("Right operand must be of type int, but got " + t2.toStr());
		}
		return new ASTTInt();
	}

		

}
