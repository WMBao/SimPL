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

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = e1.typecheck(E);
        TypeResult rtr = e2.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type t1 = substitution.apply(ltr.t);
        substitution = t1.unify(Type.BOOL).compose(substitution);
        return TypeResult.of(substitution,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        BoolValue v1 = (BoolValue)e1.eval(s);
        if (v1.b) {
            // E-Loop1
            return new Seq(e2,this).eval(s);
//            return (new Loop(e1,e2)).eval(s);
        }else
            // E-Loop2
            return Value.UNIT;
    }
}
