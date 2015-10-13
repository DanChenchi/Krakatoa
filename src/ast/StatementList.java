package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class StatementList {

    public StatementList() {
        statementList = new ArrayList<Statement>();
    }

    public StatementList(ArrayList<Statement> statementList) {
        this.statementList = statementList;
    }

    public void addElement(Statement statement) {
        statementList.add(statement);
    }

    public void addList(StatementList statementList){
        this.statementList.addAll(statementList.getStatementList());
    }

    public Iterator<Statement> elements() {
        return this.statementList.iterator();
    }

    public int getSize() {
        return statementList.size();
    }

    public ArrayList<Statement> getStatementList() {
        return statementList;
    }

    private ArrayList<Statement> statementList;

	public void genKra(PW pw) {
		if(!statementList.isEmpty()){
			for (Statement statement : statementList) {
				if (statement != null)
					statement.genKra(pw);
			}
		}
	}
}
