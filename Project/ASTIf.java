import java.util.Map;
import java.util.HashMap;

public class ASTIf implements ASTNode {
    ASTNode cond;
    ASTNode thenn;
    ASTNode elsee;

    public ASTIf(ASTNode cond, ASTNode thenn, ASTNode elsee) {
        this.cond = cond;
        this.thenn = thenn;
        this.elsee = elsee;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        VBool b = (VBool) cond.eval(e);
        if (b.getval()) {
            return thenn.eval(e);
        } else {
            return elsee.eval(e);
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType tcond = cond.typecheck(e);

        try {
            while (tcond instanceof ASTTId) {
                tcond = e.find(((ASTTId) tcond).toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type variable not found: " + tcond.toStr());
        }

        if (!(tcond instanceof ASTTBool)) {
            throw new TypeCheckError("Condition of if must be a boolean, found " + tcond.toStr());
        }
        
        ASTType tthen = thenn.typecheck(e);
        ASTType telse = elsee.typecheck(e);


        if (tthen.isSubtypeOf(telse, e)) {
            return telse;
        } else if (telse.isSubtypeOf(tthen, e)) {
            return tthen;
        } else {
            if (telse instanceof ASTTUnion || tthen instanceof ASTTUnion) {
                HashMap<String, ASTType> labelTypes = new java.util.HashMap<>();
                
                ASTTUnion uelse = ((ASTTUnion) telse);
                ASTTUnion uthen = ((ASTTUnion) tthen);
                
                
                for (String label : uelse.getLabels()) {
                    ASTType type = uelse.getFieldType(label);
                    labelTypes.put(label, type);
                } 

                for (String label : uthen.getLabels()) {
                    ASTType type = uthen.getFieldType(label);

                    if(labelTypes.containsKey(label)) {
                        ASTType existingType = labelTypes.get(label);
                        if (existingType.isSubtypeOf(type, e)) {
                            labelTypes.put(label, type);
                        } else if (type.isSubtypeOf(existingType, e)) {
                            // Do nothing, existingType is already a supertype
                        } else {
                            throw new TypeCheckError("Branches of if must be subtypes of each other for label " + label + ", found " + existingType.toStr() + " and " + type.toStr());
                        }
                    } else {
                        labelTypes.put(label, type);
                    }
                }

                return new ASTTUnion(new TypeBindList(labelTypes));

            } else if (tthen instanceof ASTTStruct && telse instanceof ASTTStruct) {
                ASTTStruct structThen = (ASTTStruct) tthen;
                ASTTStruct structElse = (ASTTStruct) telse;

                HashMap<String, ASTType> firstFields = new HashMap<>();
                HashMap<String, ASTType> combinedFields = new HashMap<>();


                for (String label : structThen.getLabels()) {
                    ASTType type = structThen.getFieldType(label);
                    firstFields.put(label, type);
                }

                
                for (String label: structElse.getLabels()) {
                    ASTType type = structElse.getFieldType(label);
                    if (firstFields.containsKey(label)) {
                        ASTType existingType = firstFields.get(label);
                        if (existingType.isSubtypeOf(type, e)) {
                            combinedFields.put(label, type);
                        } else if (type.isSubtypeOf(existingType, e)) {
                            combinedFields.put(label, existingType);
                        } else {
                            // Do nothing, consider extra types of supertype
                        }
                    }
                }
                return new ASTTStruct(new TypeBindList(combinedFields));

            } 
            else {
                    throw new TypeCheckError("Branches of if must be subtypes of each other, found " + tthen.toStr() + " and " + telse.toStr());
            }
        }
    
    }
}
