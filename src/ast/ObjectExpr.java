package ast;

/**
 * Created by joao on 29/09/15.
 */
public class ObjectExpr extends Expr {

    private KraClass kraClass;

    public ObjectExpr (KraClass kraClass){
        this.kraClass = kraClass;
    }

    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {

    }

    @Override
    public Type getType() {
        return kraClass;
    }
}
