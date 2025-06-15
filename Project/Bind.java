class Bind {
    private final String id;
    private final ASTNode exp;
    private final ASTType type;

    public Bind( String _id, ASTNode _exp, ASTType _type) {
        this.id = _id;
        this.exp = _exp;
        this.type = _type;
    }

    public String getId() {
        return id;
    }

    public ASTNode getExp() {
        return exp;
    }

    public ASTType getType() {
        return type;
    }
}
