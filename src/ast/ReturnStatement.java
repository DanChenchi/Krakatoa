package ast;

public class ReturnStatement extends Statement {

    private Expr expr;

    public ReturnStatement(Expr expr){
        this.expr = expr;
    }

    public Type getReturnType(){
        return expr.getType();
    }

    @Override
    public void genKra(PW pw){
    	pw.printIdent("return ");
    	this.expr.genKra(pw, false);
    	pw.println(";");
    }

    @Override
    public void genC(PW pw) {

    }
}