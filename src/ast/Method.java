package ast;

import lexer.Symbol;

public class Method {
	private String name;
	private ParamList parameters;
	private Type returnType;
	private boolean isStatic;
	private boolean isFinal;
	private StatementList statementList;
	private Symbol access;
	
	
	
	public Method(){
		this.name = null;
		this.setParameters(null);
		this.returnType = Type.voidType;
		this.isStatic = false;
		this.isFinal = false;
		this.setStatementList(new StatementList());
	}
	
	public Method(String name, ParamList parameters, Type returnType, boolean isStatic, boolean isFinal){
		this.name = name;
		this.setParameters(parameters);
		this.returnType = returnType;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.setStatementList(new StatementList());
	}	
	
	public boolean checkSignature(Method m){
		return this.equals(m);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Type getType() {
		return returnType;
	}
	public void setType(Type returnType) {
		this.returnType = returnType;
	}

	public ParamList getParameters() {
		return parameters;
	}
	public void setParameters(ParamList parameters) {
		this.parameters = parameters;
	}

	public StatementList getStatementList() {
		return statementList;
	}

	public void setStatementList(StatementList statementList) {
		this.statementList = statementList;
	}
	
	public boolean isStatic(){
		return this.isStatic;
	}
	
	public boolean isFinal(){
		return this.isFinal;
	}

	public Symbol getAccess() {
		return access;
	}

	public void setAccess(Symbol access) {
		this.access = access;
	}
}
