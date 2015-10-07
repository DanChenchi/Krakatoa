package ast;

import java.io.ObjectInputStream.GetField;

public class WriteStatement extends Statement{

	private ExprList exprList;
	
	public WriteStatement(){
		this.exprList = new ExprList();
	}
	
	public WriteStatement(ExprList exprList){
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
