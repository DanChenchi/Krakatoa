package ast;

public class CompositeStatement extends Statement {

    private StatementList statementList;

    public CompositeStatement(StatementList statementList){
        this.statementList = statementList;
    }

    @Override
    public void genKra(PW pw){
/*
 * {
 * 		statementList
 * }
 */
    	pw.println(" {");
    	pw.add();
    	statementList.genKra(pw);
    	pw.sub();
    	pw.printlnIdent("}");
    }

    @Override
    public void genC(PW pw) {

    }
}
