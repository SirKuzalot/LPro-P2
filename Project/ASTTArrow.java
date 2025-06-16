import java.util.HashSet;
import java.util.Set;

public class ASTTArrow implements ASTType {
    ASTType dom;
    ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        dom = d;
        codom = co;
    }

    public ASTType getDom() {
        return dom;
    }
    public ASTType getCodom() {
        return codom;
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        if (other instanceof ASTTArrow) {
            ASTTArrow otherArrow = (ASTTArrow) other;
            return otherArrow.getDom().isSubtypeOf(dom, e) && codom.isSubtypeOf(otherArrow.getCodom(), e);
        }
        return false;
    }

    public String toStr() {
        return dom.toStr()+"->"+codom.toStr();
    }   

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {

        dom = dom.simplify(e, new HashSet<String>(visited));
        codom = codom.simplify(e, visited);
        return this;
    }
}

