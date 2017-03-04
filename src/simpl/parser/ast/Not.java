package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult tr = e.typecheck(E);
        Substitution substitution = tr.s;
        Type t = substitution.apply(tr.t);
        substitution = t.unify(Type.BOOL).compose(substitution);
        return TypeResult.of(substitution,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Not1 & E-Not2
        BoolValue v = (BoolValue)e.eval(s);
        return new BoolValue(!(v.b));
    }
}
