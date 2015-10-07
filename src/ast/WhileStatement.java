package ast;

public class WhileStatement extends Statement{

	
	private Expr expression;
	private Statement statement;
	
	public WhileStatement() {
		this.setExpression(null);
		this.setStatement(null);
	}
	
	public WhileStatement(Expr expr, Statement statement){
		this.setExpression(expr);
		this.setStatement(statement);
	}
	
	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public Expr getExpression() {
		return expression;
	}

	public void setExpression(Expr expression) {
		this.expression = expression;
	}

}
