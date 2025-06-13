public class ASTTArrow implements ASTType {
    ASTType dom;
    ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        dom = d;
        codom = co;
    }

    public String toStr() {
        return dom.toStr()+"->"+codom.toStr();
    }   
}

