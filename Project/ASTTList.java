import java.util.Set;

public class ASTTList implements ASTType {
    private ASTType elt;

    public ASTTList(ASTType eltt)
    {
        elt = eltt;
    }
    
    public String toStr() {
        return "list<"+elt.toStr()+">";
    }

    public ASTType getElementType() {
        return elt;
    }
    
    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        if (other instanceof ASTTList) {
            ASTTList otherList = (ASTTList) other;
            return elt.isSubtypeOf(otherList.elt, e);
        }
        return false;
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {

        elt = elt.simplify(e, visited);
        return this;

    }
}
