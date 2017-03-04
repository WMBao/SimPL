package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Sub extends ArithExpr {

    public Sub(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " - " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        IntValue lv = (IntValue)l.eval(s);
        IntValue rv = (IntValue)r.eval(s);
        return new IntValue(lv.n - rv.n);
    }
}
