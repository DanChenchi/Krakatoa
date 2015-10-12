package ast;

/**
 * Created by joao on 29/09/15.
 */
public class WriteStatement extends Statement {

    private ExprList exprList;

    public WriteStatement (ExprList exprList){
        this.exprList = exprList;
    }

    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
