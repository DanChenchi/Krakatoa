package ast;

public class WriteStatement extends Statement {

    private ExprList exprList;

    public WriteStatement (ExprList exprList){
        this.exprList = exprList;
    }

    public void genKra(PW pw){
    	pw.printIdent("write( ");
    	this.exprList.genKra(pw);
    	pw.println(" );");
    }

    @Override
    public void genC(PW pw) {

    }
}
