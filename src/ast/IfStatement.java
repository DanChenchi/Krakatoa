package ast;

/**
 * Created by joao on 29/09/15.
 */
public class IfStatement extends Statement {

    private Expr expr;
    private Statement ifStatement;
    private Statement elseStatement;

    public IfStatement(Expr expr, Statement ifStatement, Statement elseStatement){
        this.expr = expr;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
