import java.util.HashMap;
import java.util.Map;

public class VRecord implements IValue {
    private HashMap<String, IValue> fields;

    public VRecord(HashMap<String, IValue> fields0) {
        fields = fields0;
    }

    public IValue get(String key) {
        return fields.get(key);
    }

    public String toStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        boolean first = true;
        for (Map.Entry<String, IValue> entry : fields.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(" = ").append(entry.getValue().toStr());
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }
    
}