package simpl.parser.ast;

import simpl.typing.ListType;
import simpl.typing.PairType;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult ltr = l.typecheck(E);
        TypeResult rtr = r.typecheck(E);
        Substitution substitution = rtr.s.compose(ltr.s);
        Type lt = substitution.apply(ltr.t);
        Type rt = substitution.apply(rtr.t);

        if(lt instanceof ListType || rt instanceof ListType){
            Type tv = new TypeVar(false);
            substitution = lt.unify(new ListType(tv)).compose(substitution);
            tv = substitution.apply(tv);
            substitution = rt.unify(new ListType(tv)).compose(substitution);
        }else if(lt instanceof PairType || rt instanceof PairType){
            Type ltv = new TypeVar(false);
            Type rtv = new TypeVar(false);
            substitution = lt.unify(new PairType(ltv,rtv)).compose(substitution);
            ltv = substitution.apply(ltv);
            rtv = substitution.apply(rtv);
            substitution = rt.unify(new PairType(ltv,rtv)).compose(substitution);
        }else if(lt instanceof RefType || rt instanceof RefType){
            Type tv = new TypeVar(false);
            substitution = lt.unify(new RefType(tv)).compose(substitution);
            tv = substitution.apply(tv);
            substitution = rt.unify(new RefType(tv)).compose(substitution);
        }
        else if(lt instanceof TypeVar && rt instanceof TypeVar){
            Type tv = new TypeVar(false);
            substitution = lt.unify(tv).compose(substitution);
            tv = substitution.apply(tv);
            substitution = rt.unify(tv).compose(substitution);
        } else if(lt.equals(Type.INT) || rt.equals(Type.INT)){
            substitution = rt.unify(Type.INT).compose(lt.unify(Type.INT)).compose(substitution);
        }else if(lt.equals(Type.BOOL) || rt.equals(Type.BOOL)){
            substitution = rt.unify(Type.BOOL).compose(lt.unify(Type.BOOL)).compose(substitution);
        }
        return TypeResult.of(substitution,Type.BOOL);
    }
}
