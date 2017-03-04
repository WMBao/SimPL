package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);

        if (lt instanceof TypeVar) {
            TypeVar tv = new TypeVar(false); 
            substitution = lt.unify(new ArrowType(rt,tv)).compose(substitution);
            return TypeResult.of(substitution, substitution.apply(tv));
        }
        if (lt instanceof ArrowType) {
            substitution = ((ArrowType)lt).t1.unify(rt).compose(substitution);
            lt = substitution.apply(lt);
            return TypeResult.of(substitution, ((ArrowType)lt).t2);
        }
        throw new TypeError("No func found");
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-App
        FunValue fv = (FunValue)l.eval(s);
        Value v = r.eval(s);
        return fv.e.eval(State.of(new Env(fv.E, fv.x, v),s.M,s.p));
    }
}
