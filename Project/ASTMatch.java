import java.util.HashMap;

public class ASTMatch implements ASTNode {
    ASTNode expr;
    ASTNode nilCase;
    String headId, tailId;
    ASTNode consCase;
    public ASTMatch(ASTNode expr, ASTNode nilCase, String headId, String tailId, ASTNode consCase) {
        this.expr = expr;
        this.nilCase = nilCase;
        this.headId = headId;
        this.tailId = tailId;
        this.consCase = consCase;
    }
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = expr.eval(e);
        if (v instanceof VNil) {
            return nilCase.eval(e);
        } else if (v instanceof VCons) {
            VCons cons = (VCons) v;
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, cons.getHead());
            env2.assoc(tailId, cons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else if (v instanceof VLazyCons) {
            VLazyCons lazyCons = (VLazyCons) v;
            lazyCons.force();
            Environment<IValue> env2 = e.beginScope();
            env2.assoc(headId, lazyCons.getHead());
            env2.assoc(tailId, lazyCons.getTail());
            IValue result = consCase.eval(env2);
            env2.endScope();
            return result;
        } else {
            throw new InterpreterError("Match on non-list value");
        }
    }

    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType exprType = expr.typecheck(e);

        try {
            while (exprType instanceof ASTTId) {
                exprType = e.find(exprType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + exprType.toStr() + " not found in environment");
        }

        
        if (!(exprType instanceof ASTTList)) {
            throw new TypeCheckError("Match expression must be a list, found: " + exprType.toStr());
        }
        ASTTList listType = (ASTTList) exprType;
        ASTType nilType = nilCase.typecheck(e);

        Environment<ASTType> env = e.beginScope();
        try {
            env.assoc(headId, listType.getElementType());
            env.assoc(tailId, listType);
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Error associating identifiers in match: " + ex.getMessage());
        }

        ASTType consType = consCase.typecheck(env);

        if (nilType.isSubtypeOf(consType, env)) {
            return consType;
        } else if (consType.isSubtypeOf(nilType, env)) {
            return nilType;
        } else if (nilType instanceof ASTTUnion && consType instanceof ASTTUnion) {

            HashMap<String, ASTType> labelTypes = new java.util.HashMap<>();
            
            ASTTUnion unil = ((ASTTUnion) nilType);
            ASTTUnion ucons = ((ASTTUnion) consType);
            
            
            for (String label : unil.getLabels()) {
                ASTType type = unil.getFieldType(label);
                labelTypes.put(label, type);
            } 

            for (String label : ucons.getLabels()) {
                ASTType type = ucons.getFieldType(label);

                if(labelTypes.containsKey(label)) {
                    ASTType existingType = labelTypes.get(label);
                    if (existingType.isSubtypeOf(type, e)) {
                        labelTypes.put(label, type);
                    } else if (type.isSubtypeOf(existingType, e)) {
                        // Do nothing, existingType is already a supertype
                    } else {
                        throw new TypeCheckError("Branches of match must be subtypes of each other for label " + label + ", found " + existingType.toStr() + " and " + type.toStr());
                    }
                } else {
                    labelTypes.put(label, type);
                }
            }
            return new ASTTUnion(new TypeBindList(labelTypes));
        } else if ( nilType instanceof ASTTStruct && consType instanceof ASTTStruct) {
            ASTTStruct nilStruct = (ASTTStruct) nilType;
            ASTTStruct consStruct = (ASTTStruct) consType;

            
                HashMap<String, ASTType> firstFields = new HashMap<>();
                HashMap<String, ASTType> combinedFields = new HashMap<>();


                for (String label : nilStruct.getLabels()) {
                    ASTType type = nilStruct.getFieldType(label);
                    firstFields.put(label, type);
                }

                
                for (String label: consStruct.getLabels()) {
                    ASTType type = consStruct.getFieldType(label);
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
        } else {
            throw new TypeCheckError("Types of nil case and cons case do not match: " + nilType.toStr() + " and " + consType.toStr());
        }

    }
}