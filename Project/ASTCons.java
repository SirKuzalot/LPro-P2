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
}