package simpl.interpreter.pcf;

import simpl.interpreter.BoolValue;
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

public class iszero extends FunValue {

    public iszero() {
//         TODO
        super(Env.empty, Symbol.symbol("iszero arg"), new Expr() {

            @Override
            public TypeResult typecheck(TypeEnv E) throws TypeError {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Value eval(State s) throws RuntimeError {
                // TODO Auto-generated method stub
                IntValue i = (IntValue)(s.E.get(Symbol.symbol("iszero arg")));
                return new BoolValue(i.n == 0);
            }
        });
    }
}
