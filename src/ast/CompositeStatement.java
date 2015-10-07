package ast;

public class CompositeStatement extends Statement{
	private StatementList statementList;

	
	public CompositeStatement(){
		this.statementList = new StatementList();
	}
	
	public CompositeStatement(StatementList list) {
		this.statementList = list;
	}
	
	public StatementList getStatementList() {
		return statementList;
	}

	public void setStatementList(StatementList statementList) {
		this.statementList = statementList;
	}

	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}
	
}
