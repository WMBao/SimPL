package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class LessEq extends RelExpr {

    public LessEq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " <= " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//      TODO
        // E-LessEq1 & LessEq2
        IntValue lv = (IntValue)l.eval(s);
        IntValue rv = (IntValue)r.eval(s);
        return lv.n <= rv.n ? new BoolValue(true) : new BoolValue(false);
    }
}
