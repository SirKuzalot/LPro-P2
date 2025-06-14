public class VUnion implements IValue {
    private String label;
    private IValue value;

    public VUnion(String label, IValue value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public IValue getValue() { return value; }

    @Override
    public String toStr() {
        if (value instanceof VUnit) {
            return label + "()";
        }
        return label + "(" + value.toStr() + ")";
    }
}