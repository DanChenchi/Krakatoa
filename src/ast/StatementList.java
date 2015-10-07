package ast;

import java.util.ArrayList;

public class StatementList {
	private ArrayList<Statement> statementList;
	
	public StatementList(){
		this.setStatementList(new ArrayList<Statement>());
	}
	
	public StatementList(ArrayList<Statement> list){
		this.setStatementList(list);
	}
	
	public void addStatement(Statement statement){
		this.statementList.add(statement);
	}

	public ArrayList<Statement> getStatementList() {
		return statementList;
	}
	public void setStatementList(ArrayList<Statement> statementList) {
		this.statementList = statementList;
	}
	
	
}
