package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult tr = e.typecheck(E);
        Substitution substitution = tr.s;
        Type t = substitution.apply(tr.t);
        substitution = t.unify(Type.INT).compose(substitution);
        return TypeResult.of(substitution,Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Neg
        IntValue v = (IntValue)e.eval(s);
        return new IntValue(-(v.n));
    }
}
