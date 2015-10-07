package ast;

public class ReturnStatement extends Statement{

	private Expr expression;
	
	public ReturnStatement() {
		this.setExpression(null);
	}
	
	public ReturnStatement(Expr expression){
		this.setExpression(expression);
	}
	
	public Type getReturnType(){
		return this.expression.getType();
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

}
