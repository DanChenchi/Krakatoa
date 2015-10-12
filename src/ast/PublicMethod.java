package ast;

import lexer.Symbol;

public class PublicMethod extends Method{
	private Symbol qualifier;
	
	public PublicMethod(String name, ParamList parameters, Type returnType, boolean isStatic, boolean isFinal){
		super(returnType, name, isStatic, isFinal, parameters);
		this.qualifier = Symbol.PUBLIC;
	}
	
	public void genKra(PW pw){
		super.genKra(pw, "public");
	}
}
