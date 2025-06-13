public class VNil implements IValue {
    // VNil is a singleton class
    private static final VNil instance = new VNil();

    // Private constructor to prevent instantiation
    private VNil() {}

    // Static method to get the single instance of VNil
    public static VNil getInstance() {
        return instance;
    }

    @Override
    public String toStr() {
        return "nil";
    }
}
