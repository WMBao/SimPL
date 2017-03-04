package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class succ extends FunValue {

    public succ() {
//         TODO
        super(Env.empty, Symbol.symbol("succ arg"), new Expr() {

            @Override
            public TypeResult typecheck(TypeEnv E) throws TypeError {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                // TODO Auto-generated method stub
                IntValue i = (IntValue)(s.E.get(Symbol.symbol("succ arg")));
                return new IntValue(i.n+1);
            }
        });
    }
}
