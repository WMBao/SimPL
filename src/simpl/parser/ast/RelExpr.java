package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);
        substitution = rt.unify(Type.INT).compose(lt.unify(Type.INT)).compose(substitution);
        return TypeResult.of(substitution,Type.BOOL);
    }
}
