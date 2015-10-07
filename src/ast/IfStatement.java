package ast;

public class IfStatement extends Statement{

	private Expr expression;
	private Statement ifStatement;
	private Statement elseStatement;
	
	public IfStatement(){
		this.expression = null;
		this.ifStatement = null;
		this.elseStatement = null;
	}
	
	public IfStatement(Expr expression, Statement ifStatement, Statement elseStatement){
		this.expression = expression;
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
	}
	
	
	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

	public Expr getExpression() {
		return expression;
	}
	public void setExpression(Expr expression) {
		this.expression = expression;
	}

	public Statement getIfStatement() {
		return ifStatement;
	}
	public void setIfStatement(Statement ifStatement) {
		this.ifStatement = ifStatement;
	}

	public Statement getElseStatement() {
		return elseStatement;
	}
	public void setElseStatement(Statement elseStatement) {
		this.elseStatement = elseStatement;
	}
	
}
