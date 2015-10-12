package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ReadStatement extends Statement {

    private ExprList exprList;

    public ReadStatement (ExprList exprList){
        this.exprList = exprList;
    }

    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
