package simpl.parser.ast;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        Type xt = new TypeVar(false);
        TypeResult tr = e.typecheck(TypeEnv.of(E, x, xt));
        Type ft = new TypeVar(false);
        Substitution substitution = ft.unify(tr.t).compose(tr.s);
        xt = substitution.apply(xt);
        ft = substitution.apply(ft);
        return TypeResult.of(substitution,new ArrowType(xt,ft));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Fn
        return new FunValue(s.E,x,e);
    }
}
