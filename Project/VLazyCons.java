public class VLazyCons implements IValue {
    private ASTNode headNode, tailNode;
    private Environment<IValue> env;
    private IValue headValue = null, tailValue = null;
    private boolean evaluated = false;

    public VLazyCons(ASTNode headNode, ASTNode tailNode, Environment<IValue> env) {
        this.headNode = headNode;
        this.tailNode = tailNode;
        this.env = env;
    }

    // Called when the list is matched (opened)
    public void force() throws InterpreterError {
        if (!evaluated) {
            headValue = headNode.eval(env);
            tailValue = tailNode.eval(env);
            evaluated = true;
        }
    }

    public IValue getHead() throws InterpreterError {
        return headValue;
    }

    public IValue getTail() throws InterpreterError {
        return tailValue;
    }

    public String toStr() {
        if (evaluated) {
            try {
                return headValue.toStr() + "::" + getTail().toStr();
            } catch (InterpreterError e) {
                return "LazyCons(?, ?)";
            }
        } else {
            return "Unevaluated LazyCons";
        } 
    }

}
