package ast;

/**
 * Created by joao on 29/09/15.
 */
public class WhileStatement extends Statement {

    private Expr expr;
    private Statement statement;

    public WhileStatement(Expr expr, Statement statement){
        this.expr = expr;
        this.statement = statement;
    }

    public void genKra (PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
