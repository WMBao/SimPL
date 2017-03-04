package simpl.interpreter.lib;

import simpl.interpreter.ConsValue;
import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class hd extends FunValue {

    public hd() {
//         TODO
        super(Env.empty, Symbol.symbol("hd arg"), new Expr() {

            @Override
            public TypeResult typecheck(TypeEnv E) throws TypeError {
//                 TODO Auto-generated method stub
                return TypeResult.of(new TypeVar(true));
            }

            @Override
            public Value eval(State s) throws RuntimeError {
//                 TODO Auto-generated method stub
                Value v = s.E.get(Symbol.symbol("hd arg"));
                if (v == Value.NIL) {
                    // hd(nil) = error;
                    throw new RuntimeError("ERROR: hd(nil)");
                }else
                    // hd(cons,v1,v2) = v1;
                    return ((ConsValue)v).v1;
            }
        });
    }
}
