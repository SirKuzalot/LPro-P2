
public class ASTFieldSelect implements ASTNode {
    private ASTNode record;
    private String field;

    public ASTFieldSelect(ASTNode record0, String field0) {
        record = record0;
        field = field0;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue recVal = record.eval(env);
        VStruct vrec = (VStruct) recVal;
        IValue val = vrec.get(field);
        if (val == null) {
            throw new InterpreterError("Field '" + field + "' not found in record");
        }
        return val;
    }

    public ASTType typecheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType recordType = record.typecheck(env);

        try {
            while (recordType instanceof ASTTId) {
                recordType = env.find(recordType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + recordType.toStr() + " not found in environment");
        }

        if (!(recordType instanceof ASTTStruct)) {
            throw new TypeCheckError("Field select on non-record type: " + recordType.toStr());
        }
        ASTTStruct structType = (ASTTStruct) recordType;
        ASTType fieldType = structType.getFieldType(field);

        try {
            while (fieldType instanceof ASTTId) {
                fieldType = env.find(fieldType.toStr());
            }
        } catch (InterpreterError ex) {
            throw new TypeCheckError("Type " + fieldType.toStr() + " not found in environment");
        }
        
        if (fieldType == null) {
            throw new TypeCheckError("Field '" + field + "' not found in record type");
        }

        return fieldType;
    }
}