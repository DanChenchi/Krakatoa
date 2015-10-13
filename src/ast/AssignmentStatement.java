package ast;

public class AssignmentStatement extends Statement {

    private Expr leftExpr, rightExpr;

    public AssignmentStatement (Expr leftExpr, Expr rightExpr){
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }

    @Override
    public void genKra(PW pw){
    	pw.printIdent("");
    	this.leftExpr.genKra(pw, false);
    	if(this.rightExpr != null){
    		pw.print(" = ");
    		this.rightExpr.genKra(pw, false);
    	}
    	pw.println(";");
    }

    @Override
    public void genC(PW pw) {

    }
}
