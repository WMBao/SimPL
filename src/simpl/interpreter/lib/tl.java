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

public class tl extends FunValue {

    public tl() {
//         TODO
        super(Env.empty, Symbol.symbol("tl arg"), new Expr() {

            @Override
            public TypeResult typecheck(TypeEnv E) throws TypeError {
//                 TODO Auto-generated method stub
                return TypeResult.of(new TypeVar(true));
            }

            @Override
            public Value eval(State s) throws RuntimeError {
//                 TODO Auto-generated method stub
                Value v = s.E.get(Symbol.symbol("tl arg"));
                if (v == Value.NIL) {
                    // tl(cons,v1,v2) = v2;
                    throw new RuntimeError("ERROR: tl(nil)");
                }else
                    // tl(nil) = error;
                    return ((ConsValue)v).v2;
            }
        });
    }
}