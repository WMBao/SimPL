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
import simpl.typing.TypeVar;

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult tr1 = e1.typecheck(E);
        TypeResult tr2 = e2.typecheck(E);
        TypeResult tr3 = e3.typecheck(E);
        Substitution substitution = tr3.s.compose(tr2.s).compose(tr1.s);
        Type t1 = substitution.apply(tr1.t);
        Type t2 = substitution.apply(tr2.t);
        Type t3 = substitution.apply(tr3.t);
        substitution = t1.unify(Type.BOOL).compose(substitution);
        t2 = substitution.apply(t2);
        t3 = substitution.apply(t3);
        TypeVar rt = new TypeVar(false);
        substitution = t2.unify(rt).compose(substitution);
        t3 = substitution.apply(t3);
        substitution = t3.unify(rt).compose(substitution);
        return TypeResult.of(substitution,substitution.apply(rt));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        BoolValue v1 = (BoolValue)e1.eval(s);
        if (v1.b)
            // E-Cond1
            return e2.eval(s);
        else
            // E-Cond2
            return e3.eval(s);
    }
}
