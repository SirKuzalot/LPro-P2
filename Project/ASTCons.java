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

        try {
            while (headType instanceof ASTTId) {
                headType = e.find(headType.toStr());
            }

            while (tailType instanceof ASTTId) {
                tailType = e.find(tailType.toStr());
            }
            
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + headType.toStr() + " not found in environment");
        }

        if (!(tailType instanceof ASTTList)) {
            try {
                while (tailType instanceof ASTTId) {
                    tailType = e.find(tailType.toStr());
                }
            } catch (InterpreterError ex) {
                throw new TypeCheckError("Type " + tailType.toStr() + " not found in environment");
            }

            if (tailType instanceof ASTTUnit) {
                return new ASTTList(headType);
            } else {
                throw new TypeCheckError("Tail must be a list or nil, found: " + tailType.toStr());
            }

        }

        ASTTList listType = (ASTTList) tailType;

        if (!listType.getElementType().equals(headType)) {
            throw new TypeCheckError("Head type does not match tail list element type");
        }

        return listType;

    }
}