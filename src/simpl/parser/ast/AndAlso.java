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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);
        substitution = rt.unify(Type.BOOL).compose(lt.unify(Type.BOOL)).compose(substitution);
        return TypeResult.of(substitution,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        BoolValue lv = (BoolValue)l.eval(s);
        if (!lv.b)
            // E-AndAlso2
            return new BoolValue(false);
        else {
            BoolValue rv = (BoolValue)r.eval(s);
            // E-AndAlso1
            return new BoolValue(rv.b);
        }
    }
}
