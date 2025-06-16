import java.util.HashSet;
import java.util.Set;

public class ASTTUnion implements ASTType {

    private TypeBindList ll;

    public ASTTUnion(TypeBindList llp) {
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
        sb.append("union { ");
        for (String label : ll.getLabels()) {
            sb.append(label).append(": ").append(ll.getType(label).toStr()).append("; ");
        }
        sb.append("}");
        return sb.toString();
    }

    public boolean isSubtypeOf(ASTType other, Environment<ASTType> e) {
        if (other instanceof ASTTUnion) {
            ASTTUnion otherUnion = (ASTTUnion) other;
            TypeBindList otherFields = otherUnion.getTypeBindList();
            TypeBindList thisFields = this.getTypeBindList();

            for (String field : thisFields.getLabels()) {
                ASTType otherFieldType = otherFields.getType(field);
                if (otherFieldType == null) {
                    return false;
                }
            }

            for (String field : thisFields.getLabels()) {
                ASTType thisFieldType = thisFields.getType(field);
                ASTType otherFieldType = otherFields.getType(field);
                if (!otherFieldType.isSubtypeOf(thisFieldType, e)) {
                    return false;
                }
            }
            return true;
        } else if (other instanceof ASTTId) {
            ASTTId otherId = (ASTTId) other;
            try {
                other = e.find(otherId.toStr());
            } catch (InterpreterError ex) {
                return false;
            }
            return this.isSubtypeOf(other, e);
        }

        return false;
    }

}