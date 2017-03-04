package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        return TypeResult.of(substitution, substitution.apply(rtr.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        l.eval(s);
        return r.eval(s);
    }
}
