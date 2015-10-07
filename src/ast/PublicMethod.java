package ast;

import lexer.Symbol;

public class PublicMethod extends Method{
	private Symbol qualifier;
	
	public PublicMethod(String name, ParamList parameters, Type returnType, boolean isStatic, boolean isFinal){
		super(name, parameters, returnType, isStatic, isFinal);
		this.qualifier = Symbol.PUBLIC;
	}
}
