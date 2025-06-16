import java.util.HashSet;
import java.util.Set;

public class ASTTStruct implements ASTType {

    private TypeBindList ll;

    public ASTTStruct(TypeBindList llp) {
        ll = llp;
    }

    public TypeBindList getTypeBindList() {
        return ll;
    }

    public ASTType getFieldType(String fieldName) {
        return ll.getType(fieldName);
    }
    
    public String toStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("struct { ");
        for (String label : ll.getLabels()) {
            sb.append(label).append(": ").append(ll.getType(label).toStr()).append("; ");
        }
        sb.append("}");
        return sb.toString();
    }
    
    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        if (other instanceof ASTTStruct) {
            ASTTStruct otherStruct = (ASTTStruct) other;
            TypeBindList otherFields = otherStruct.getTypeBindList();
            TypeBindList thisFields = this.getTypeBindList();

            for (String field : otherFields.getLabels()) {
                ASTType thisFieldType = thisFields.getType(field);
                if (thisFieldType == null) {
                    return false;
                }
            }

            for (String field : otherFields.getLabels()) {
                ASTType thisFieldType = thisFields.getType(field);
                ASTType otherFieldType = otherFields.getType(field);
                if (!thisFieldType.isSubtypeOf(otherFieldType, e)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public ASTType simplify(Environment<ASTType> e, Set<String> visited) throws InterpreterError {

        for (String field : ll.getLabels()) {
            ASTType fieldType = ll.getType(field);
            ASTType simplifiedFieldType = fieldType.simplify(e, new HashSet<String>(visited));
            ll.setType(field, simplifiedFieldType);
        }

        return this;
    }

}