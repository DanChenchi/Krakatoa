package ast;

import lexer.Symbol;

public class PrivateMethod extends Method{
	private Symbol qualifier;

	
	public PrivateMethod(String name, ParamList parameters, Type returnType, boolean isStatic, boolean isFinal){
		super(name, parameters, returnType, isStatic, isFinal);
		this.qualifier = Symbol.PRIVATE;
	}
}
