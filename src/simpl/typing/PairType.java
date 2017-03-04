package simpl.typing;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
//         TODO
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
//         TODO
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        if(t instanceof PairType){
            Substitution s1 = ((PairType)t).t2.unify(this.t2);
            Substitution s2 = s1.apply(((PairType)t).t1).unify(s1.apply(this.t1));
            return s2.compose(s1);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
//         TODO
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
//         TODO
        t1 = t1.replace(a,t);
        t2 = t2.replace(a,t);
        return this;
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
