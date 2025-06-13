import java.util.*;

public class Environment <E>{
    Environment<E> anc;
    Map<String, E> bindings;

    Environment(){
        anc = null;
        bindings = new HashMap<String,E>();
    }
    
    Environment(Environment<E> ancestor){
        anc = ancestor;
        bindings = new HashMap<String,E>();
    }

    Environment<E> beginScope(){
        return new Environment<E>(this);
    }
    
    Environment<E> endScope(){
        return anc;
    }

    void assoc(String id, E bind) throws InterpreterError {
        if (bindings.containsKey(id)) {
            throw new InterpreterError("Duplicate binding for " + id);
        }
        bindings.put(id, bind);
    }

    E find(String id) throws InterpreterError {
        if (bindings.containsKey(id)) {
            return bindings.get(id);
        } else if (anc != null) {
            return anc.find(id);
        } else {
            throw new InterpreterError("Unbound identifier " + id);
        }
    }

}
