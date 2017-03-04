package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Mod extends ArithExpr {

    public Mod(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " % " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Mod
        IntValue lv = (IntValue)l.eval(s);
        IntValue rv = (IntValue)r.eval(s);
        if (rv.n == 0)
            throw new RuntimeError("Error: MOD 0");
        return new IntValue(lv.n % rv.n);
    }
}
