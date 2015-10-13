package ast;

public class DeclarationStatement extends Statement {

    private VariableList variableList;
    private Type type;

    public DeclarationStatement (Type type, VariableList variableList){
        this.variableList = variableList;
        this.type = type;
    }

    @Override
    public void genC(PW pw) {

    }

	@Override
	public void genKra(PW pw) {
		pw.printIdent(this.type.getName());
		pw.print(" ");
		this.variableList.genKra(pw);
		pw.println(";");
		
	}
}
