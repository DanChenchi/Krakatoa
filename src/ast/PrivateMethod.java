package ast;

import lexer.Symbol;

public class PrivateMethod extends Method{
	private Symbol qualifier;

	
	public PrivateMethod(String name, ParamList parameters, Type returnType, boolean isStatic, boolean isFinal){
		super(returnType, name, isStatic, isFinal, parameters);
		this.qualifier = Symbol.PRIVATE;
	}
	
	public Symbol getQualifier() {
		return qualifier;
	}

	public void setQualifier(Symbol qualifier) {
		this.qualifier = qualifier;
	}
	
	public void genKra(PW pw){
		super.genKra(pw, "private");
	}
}
