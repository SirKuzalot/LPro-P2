import java.util.*;

public class TypeBindList  {

private HashMap<String,ASTType> lbl;

public TypeBindList(HashMap<String,ASTType> ll) {
        lbl = ll;
} 

public List<String> getLabels() {
        return new ArrayList<>(lbl.keySet());
}

public List<ASTType> getTypes() {
        return new ArrayList<>(lbl.values());
}

public void setType(String fieldName, ASTType type) {
        lbl.put(fieldName, type);
}

public ASTType getType(String fieldName) {
        return lbl.get(fieldName);
}

}