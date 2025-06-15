public class ASTNil implements ASTNode {
    public IValue eval(Environment<IValue> e) { return VNil.getInstance(); }
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        return new ASTTList(new ASTTUnit());
    }
}