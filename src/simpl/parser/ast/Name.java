package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        Type t = E.get(x);
        if (t == null)
            throw new TypeError("Symbol not defined");
        return TypeResult.of(t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        Value v = s.E.get(x);
        if (v instanceof RecValue) {
            // E-Name1
            RecValue rv = (RecValue)v;
            return new Rec(rv.x, rv.e).eval(State.of(rv.E, s.M, s.p));
        }else
            // E-Name2
            return v;
    }
}
