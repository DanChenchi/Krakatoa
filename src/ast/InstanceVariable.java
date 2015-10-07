package ast;

import lexer.Symbol;

public class InstanceVariable extends Variable {

	private Symbol qualifier;
	private boolean isStatic;
	private boolean isFinal;
	
	
    public InstanceVariable(Type type, String name, boolean isStatic, boolean isFinal) {
        super(name, type);
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }
    
    public boolean isStatic(){
    	return this.isStatic;
    }
    
    public boolean isFinal(){
    	return this.isFinal;
    }

}