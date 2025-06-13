public class VCons implements IValue {
    private final IValue head;
    private final IValue tail;

    // Strict cons
    public VCons(IValue head, IValue tail) {
        this.head = head;
        this.tail = tail;
    }

    public IValue getHead() { return head; }
    public IValue getTail() { return tail; }
    public String toStr() {
        return head.toStr() + "::" + getTail().toStr();
    }
}