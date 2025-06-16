public class ASTMatch implements ASTNode {
    ASTNode expr;
    ASTNode nilCase;
    String headId, tailId;
    ASTNode consCase;
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headId, String tailId, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headId = headId;
        this.tailId = tailId;
        this.consCase = consCase;
    }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        if (v instanceof VNil) {
            return nilCase.eval(e);
        } else if (v instanceof VCons) {
            VCons cons = (VCons) v;
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, cons.getHead());
            env2.assoc(tailId, cons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else if (v instanceof VLazyCons) {
            VLazyCons lazyCons = (VLazyCons) v;
            lazyCons.force();
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, lazyCons.getHead());
            env2.assoc(tailId, lazyCons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else {
            throw new InterpreterError("Match on non-list value");
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType exprType = expr.typecheck(e);

        try {
            while (exprType instanceof ASTTId) {
                exprType = e.find(exprType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + exprType.toStr() + " not found in environment");
        }

        
        if (!(exprType instanceof ASTTList)) {
            throw new TypeCheckError("Match expression must be a list, found: " + exprType.toStr());
        }
        ASTTList listType = (ASTTList) exprType;
        ASTType nilType = nilCase.typecheck(e);

        Environment<ASTType> env = e.beginScope();
        try {
            env.assoc(headId, listType.getElementType());
            env.assoc(tailId, listType);
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Error associating identifiers in match: " + ex.getMessage());
        }

        ASTType consType = consCase.typecheck(env);

        if (nilType.isSubtypeOf(consType, env)) {
            return consType;
        } else if (consType.isSubtypeOf(nilType, env)) {
            return nilType;
        } else {
            throw new TypeCheckError("Types of nil case and cons case do not match: " + nilType.toStr() + " and " + consType.toStr());
        }

    }
}