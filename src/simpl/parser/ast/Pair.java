package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.PairType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);
        return TypeResult.of(substitution,new PairType(lt,rt));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Pair
        Value lv = l.eval(s);
        Value rv = r.eval(s);
        return new PairValue(lv, rv);
    }
}
