package ast;


public class MessageSendToVariable extends MessageSend { 

	private ExprList exprList;
	private String messageName;
	private KraClass kraClass;
	private Method methodCalled;
	
	public MessageSendToVariable(){
		this.setExprList(new ExprList());
		this.setMessageName(null);
		this.setKraClass(null);
		this.setMethodCalled(null);
	}
	
	public MessageSendToVariable(ExprList exprList, String messageName, KraClass kraClass){
		this.setExprList(exprList);
		this.setMessageName(messageName);
		this.setKraClass(kraClass);
		this.setMethodCalled(this.kraClass.getMethod(messageName));
	}
	
    public Type getType() {
    	if(this.methodCalled != null)
    		return this.getMethodCalled().getType();
    	else
    		return null;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        
    }

	public ExprList getExprList() {
		return exprList;
	}

	public void setExprList(ExprList exprList) {
		this.exprList = exprList;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public KraClass getKraClass() {
		return kraClass;
	}

	public void setKraClass(KraClass kraClass) {
		this.kraClass = kraClass;
	}

	public Method getMethodCalled() {
		return methodCalled;
	}

	public void setMethodCalled(Method methodCalled) {
		this.methodCalled = methodCalled;
	}

    
}    