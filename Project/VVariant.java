public class VVariant implements IValue {
    private String label;
    private IValue value;

    public VVariant(String label, IValue value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public IValue getValue() { return value; }

    @Override
    public String toStr() {
        return label + "(" + value.toStr() + ")";
    }
}