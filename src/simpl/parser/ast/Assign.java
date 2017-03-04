package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);
        Type tv = new TypeVar(false);
        substitution = lt.unify(new RefType(tv)).compose(substitution);
        tv = substitution.apply(tv);
        substitution = rt.unify(tv).compose(substitution);    
        return TypeResult.of(substitution,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Assign
        RefValue lv = (RefValue)l.eval(s);
        Value rv = r.eval(s);
        s.M.put(lv.p, rv);
        return Value.UNIT;
    }
}
