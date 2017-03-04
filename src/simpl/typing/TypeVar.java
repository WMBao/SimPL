package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
//         TODO
        if (t.contains(this)) {
            if (t instanceof TypeVar && ((TypeVar)t).name.equals(this.name))
                return Substitution.IDENTITY; // a = a;
            else
                throw new TypeCircularityError(); // Circularity (EG: a = b->a);
        }
        return Substitution.of(this,t);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
//         TODO
        return name.equals(tv.name);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
//         TODO
        if(name.equals(a.name)) 
            return t;
        else 
            return this;
    }
}
