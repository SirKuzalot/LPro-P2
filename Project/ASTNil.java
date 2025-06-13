public class ASTNil implements ASTNode {
    public IValue eval(Environment<IValue> e) { return VNil.getInstance(); }
}