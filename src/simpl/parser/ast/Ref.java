package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.Int;
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

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
//         TODO
        TypeResult tr = e.typecheck(E);
        Substitution substitution = tr.s;
        Type t = substitution.apply(tr.t);
        return TypeResult.of(substitution,new RefType(t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
//         TODO
        // E-Ref
        Int p = new Int(s.p.get());
        
        // ============ GC: Mark and Sweep ============
        int HEAPSIZE = 1; // heap size
        if(p.get() > HEAPSIZE) 
        {
            // Mark
            Env env = s.E;
            while(env != Env.empty)
            {
                Value val = env.getValue();
                while(val instanceof RefValue && val.mark == 0) 
                {   
                    val.mark = 1;
                    val = s.M.get(((RefValue)val).p);
                }
                    val.mark = 1;
                    env = env.getEnv();
            }
            
//            System.out.println("--- Before: ---");
//            for (int i = 0; i < p.get(); i++)
//            {
//                    System.out.println(s.M.get(i));
//            }
//            
            // Sweep
            for (int i = 0; i < p.get(); i++)
            {
                if (s.M.get(i) != null && s.M.get(i).mark == 0)
                {
                    s.M.put(i, null); // Utilizing the nature of Java HashMap
//                    System.out.println("collect" + i);
                }
            }
//            System.out.println("--- After: ---");
//            for (int i = 0; i < p.get(); i++)
//            {
//                    System.out.println(s.M.get(i));
//            }
//            System.out.println("GC completed");
        }
        // =================== END ===================
        
        s.p.set(s.p.get()+1);
        Value v = e.eval(s);
        s.M.put(p.get(), v);
        return new RefValue(p.get());
    }
}
