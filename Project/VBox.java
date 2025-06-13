public class VBox implements IValue {
    IValue v;

    public VBox(IValue v0) {
        v = v0;
    }

    IValue getval() {
        return v;
    }

    void setval(IValue v0) {
        v = v0;
    }

    public String toStr() {
        return "Box(" + v.toStr() + ")";
    }

}
