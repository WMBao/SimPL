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

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult tr = e.typecheck(E);
        Substitution substitution = tr.s;
        Type t = substitution.apply(tr.t);
        if (t instanceof TypeVar){
            Type tv = new TypeVar(false);
            substitution = t.unify(new RefType(tv)).compose(substitution);
            return TypeResult.of(substitution,substitution.apply(tv));
        }
        if (t instanceof RefType){
            return TypeResult.of(substitution,((RefType)t).t);
        } 
        throw new TypeError("No ref type found");
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        RefValue p1 = (RefValue)e.eval(s);
        Value v = s.M.get(p1.p);
        // E-Deref
        return v;
    }
}
