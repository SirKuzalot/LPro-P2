import java.util.*;

public class ASTFieldSelect implements ASTNode {
    private ASTNode record;
    private String field;

    public ASTFieldSelect(ASTNode record0, String field0) {
        record = record0;
        field = field0;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue recVal = record.eval(env);
        if (!(recVal instanceof VStruct)) {
            throw new InterpreterError("Field select on non-record value");
        }
        VStruct vrec = (VStruct) recVal;
        IValue val = vrec.get(field);
        if (val == null) {
            throw new InterpreterError("Field '" + field + "' not found in record");
        }
        return val;
    }
}