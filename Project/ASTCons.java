public class ASTCons implements ASTNode {
    ASTNode head, tail;
    boolean isLazy;
    public ASTCons(ASTNode head, ASTNode tail, boolean isLazy) {
        this.head = head;
        this.tail = tail;
        this.isLazy = isLazy;
    }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        if (isLazy) {
            return new VLazyCons(head, tail, e);
        } else {
            return new VCons(head.eval(e), tail.eval(e));
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType headType = head.typecheck(e);
        ASTType tailType = tail.typecheck(e);

        if (!(tailType instanceof ASTTList)) {
            throw new TypeCheckError("Tail must be a list type");
        }

        ASTTList listType = (ASTTList) tailType;

        if (!listType.getElementType().equals(headType)) {
            throw new TypeCheckError("Head type does not match tail list element type");
        }

        return listType;

    }
}