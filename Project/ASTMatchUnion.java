import java.util.*;

public class ASTMatchUnion implements ASTNode {
    private ASTNode expr;
    private List<String> labels;
    private List<String> vars;
    private List<ASTNode> bodies;

    public ASTMatchUnion(ASTNode expr, List<String> labels, List<String> vars, List<ASTNode> bodies) {
        this.expr = expr;
        this.labels = labels;
        this.vars = vars;
        this.bodies = bodies;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v = expr.eval(env);
        if (!(v instanceof VUnion)) {
            throw new InterpreterError("Match on non-variant value");
        }
        VUnion vv = (VUnion) v;
        String lbl = vv.getLabel();
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals(lbl)) {
                Environment<IValue> newEnv = new Environment<>(env);
                newEnv.assoc(vars.get(i), vv.getValue());
                return bodies.get(i).eval(newEnv);
            }
        }
        throw new InterpreterError("No matching case for label: " + lbl);
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType exprType = expr.typecheck(env);

        try {
            while (exprType instanceof ASTTId) {
                exprType = env.find(exprType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + exprType.toStr() + " not found in environment");
        }
        
        if (!(exprType instanceof ASTTUnion)) {
            throw new TypeCheckError("Match on non-variant type");
        }
        ASTTUnion unionType = (ASTTUnion) exprType;

        TypeBindList unionTypes = unionType.getTypeBindList();
        List<String> unionLabels = unionTypes.getLabels();
        List<ASTType> unionFieldTypes = unionTypes.getTypes();


        for (int i = 0; i < unionLabels.size(); i++) {
            if (!(labels.contains(unionLabels.get(i)))) {
                throw new TypeCheckError("Label " + unionLabels.get(i) + " not found in match");
            } 
        }

        List<ASTType> bodyTypes = new ArrayList<>();
        for (int i = 0; i < unionLabels.size(); i++) {
            Environment<ASTType> newEnv = env.beginScope();
            int MatchIndex = labels.indexOf(unionLabels.get(i));
            try {
                newEnv.assoc(vars.get(MatchIndex), unionFieldTypes.get(i));
            } catch (InterpreterError e) {
                throw new TypeCheckError("Variable " + vars.get(MatchIndex) + " not found for label " + unionLabels.get(i));
            }

            ASTType bodyType = bodies.get(MatchIndex).typecheck(newEnv);
            bodyTypes.add(bodyType);
        }

        for (int i = 0; i < bodyTypes.size(); i++) {
            boolean isSuperType = true;
            for (int j = 0; j < bodyTypes.size(); j++) {
                if (i == j) continue;
                if (!bodyTypes.get(j).isSubtypeOf(bodyTypes.get(i), env)) {
                    isSuperType = false;
                    break;
                }
            }
            if (isSuperType) {
                return bodyTypes.get(i);
            }
        }


        if (bodyTypes.get(0) instanceof ASTTUnion) {
            for (int i = 0; i < bodyTypes.size(); i++) {
                if (!(bodyTypes.get(i) instanceof ASTTUnion)) {
                    throw new TypeCheckError("No common supertype found for match bodies");
                }
            }

            HashMap<String, ASTType> labelTypes = new HashMap<>();
            for (ASTType bodyType : bodyTypes) {
                ASTTUnion unionBodyType = (ASTTUnion) bodyType;
                TypeBindList bodyFieldTypes = unionBodyType.getTypeBindList();
                for (String label : bodyFieldTypes.getLabels()) {
                    ASTType type = bodyFieldTypes.getType(label);
                    if (labelTypes.containsKey(label)) {
                        ASTType existingType = labelTypes.get(label);
                        if (existingType.isSubtypeOf(type, env)) {
                            labelTypes.put(label, type);
                        } else if (type.isSubtypeOf(existingType, env)) {
                            // Do nothing, existingType is already a supertype
                        } else {
                            throw new TypeCheckError("Branches of match must be subtypes of each other for label " + label + ", found " + existingType.toStr() + " and " + type.toStr());
                        }
                    } else {
                        labelTypes.put(label, type);
                    }
                }
            }
            return new ASTTUnion(new TypeBindList(labelTypes));

        } else if (bodyTypes.get(0) instanceof ASTTStruct) {
            for (int i = 1; i < bodyTypes.size(); i++) {
                if (!(bodyTypes.get(i) instanceof ASTTStruct)) {
                    throw new TypeCheckError("No common supertype found for match bodies");
                }
            }

            ASTTStruct firstBodyType = (ASTTStruct) bodyTypes.get(0);
            HashMap<String, ASTType> currentFields = new HashMap<>();

            for (String label : firstBodyType.getLabels()) {
                ASTType type = firstBodyType.getFieldType(label);
                currentFields.put(label, type);
            }

            HashMap<String, ASTType> combinedFields = new HashMap<>();
            for (int i = 1; i < bodyTypes.size(); i++) {
                ASTType bodyType = bodyTypes.get(i);
                ASTTStruct structBodyType = (ASTTStruct) bodyType;
                TypeBindList bodyFieldTypes = structBodyType.getTypeBindList();
                for (String label : bodyFieldTypes.getLabels()) {
                    ASTType type = bodyFieldTypes.getType(label);
                    if (currentFields.containsKey(label)) {
                        ASTType existingType = currentFields.get(label);
                        if (existingType.isSubtypeOf(type, env)) {
                            combinedFields.put(label, type);
                        } else if (type.isSubtypeOf(existingType, env)) {
                            combinedFields.put(label, existingType);
                        } else {
                            // Do nothing, consider extra fields of supertype
                        } 
                    }
                }

                currentFields = combinedFields;
                combinedFields = new HashMap<>();

            }
            return new ASTTStruct(new TypeBindList(currentFields));
 
            } else {
                throw new TypeCheckError("No common supertype found for match bodies");
            }



        







    }
}