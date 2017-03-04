package simpl.interpreter;

public class ConsValue extends Value {

    public final Value v1, v2;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
//         TODO
        return "(cons," + v1 + "," + v2 + ")";
    }

    @Override
    public boolean equals(Object other) {
//         TODO
        if (other instanceof NilValue) {
            return false;
        }
        if (other instanceof ConsValue) {
            return ((ConsValue)other).v1.equals(this.v1) && ((ConsValue)other).v2.equals(this.v2); 
        }
        return false;
    }
}
