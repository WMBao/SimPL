package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeVar xtv = new TypeVar(false);
        TypeResult tr = e.typecheck(TypeEnv.of(E,x,xtv));
        Substitution substitution = tr.s;
        Type xt = substitution.apply(xtv);
        Type et = substitution.apply(tr.t);
        substitution = xt.unify(et).compose(substitution);
        return TypeResult.of(substitution,substitution.apply(xt));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Rec
        Env e1 = new Env(s.E, x, new RecValue(s.E, x, e));
        return e.eval(State.of(e1, s.M, s.p));
    }
}
