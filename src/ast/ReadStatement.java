package ast;

public class ReadStatement extends Statement {

    private ExprList exprList;

    public ReadStatement (ExprList exprList){
        this.exprList = exprList;
    }

/*
 * read( exprList );
 */
    
    @Override
    public void genKra(PW pw){
    	pw.printIdent("read( ");
    	this.exprList.genKra(pw);
    	pw.println(" );");
    }

    @Override
    public void genC(PW pw) {

    }
}
