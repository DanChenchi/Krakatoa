package ast;

public class BreakStatement extends Statement {

    public BreakStatement(){}

    @Override
    public void genKra(PW pw){
    	pw.printlnIdent("break;");
    }

    @Override
    public void genC(PW pw) {

    }
}
