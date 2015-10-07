package ast;

public class ReadStatement extends Statement{
	private VariableList variableList;

	
	public ReadStatement() {
		this.variableList = new VariableList();
	}
	
	public ReadStatement(VariableList variableList){
		this.variableList = variableList;
	}
	
	public VariableList getVariableList(){
		return this.variableList;
	}
	
	public void setVariableList(VariableList variableList) {
		this.variableList = variableList;
	}
	
	@Override
	public void genC(PW pw) {
		// TODO Auto-generated method stub
		
	}
}
