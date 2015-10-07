package ast;

public class WriteLnStatement extends Statement{

	private ExprList exprList;
	
	public WriteLnStatement(){
		this.exprList = new ExprList();
	}
	
	public WriteLnStatement(ExprList exprList){
		this.exprList = exprList;
	}
	
	public ExprList getExprList(){
		return this.exprList;
	}
	
	public void setExprList(ExprList exprList){
		this.exprList = exprList;
	}
	
	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}

}
