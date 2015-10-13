package ast;

public class WriteLnStatement extends Statement {

    private ExprList exprList;

    public WriteLnStatement (ExprList exprList){
        this.exprList = exprList;
    }

    @Override
    public void genKra(PW pw){
    	pw.printIdent("writeln( ");
    	this.exprList.genKra(pw);
    	pw.println(" );");
    }

    @Override
    public void genC(PW pw) {

    }
}
