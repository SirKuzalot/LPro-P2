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

    public ASTType simplify(Environment<ASTType> e) throws InterpreterError {
        ASTType simplifiedDom = dom.simplify(e);
        ASTType simplifiedCodom = codom.simplify(e);
        return new ASTTArrow(simplifiedDom, simplifiedCodom);
    }
}

