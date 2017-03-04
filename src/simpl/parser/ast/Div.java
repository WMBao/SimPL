package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Div extends ArithExpr {

    public Div(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " / " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Div
        IntValue lv = (IntValue)l.eval(s);
        IntValue rv = (IntValue)r.eval(s);
        if (rv.n == 0)
            throw new RuntimeError("Error: DIV/0");
        return new IntValue(lv.n / rv.n);
    }
}
