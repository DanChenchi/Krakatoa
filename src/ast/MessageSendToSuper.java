package ast;

public class MessageSendToSuper extends MessageSend { 

	private ExprList exprList;
	private String messageName;
	private KraClass superClass;
	private Method methodCalled;
	
	public MessageSendToSuper(){
		this.setExprList(new ExprList());
		this.setMessageName(null);
		this.setMethodCalled(null);
		this.setSuperClass(null);
	}
	
	public MessageSendToSuper(ExprList exprList, String messageName, KraClass subClass){
		this.setExprList(exprList);
		this.setMessageName(messageName);
		this.setSuperClass(subClass.getSuperclass());
		
		this.setMethodCalled( this.superClass.getMethod(messageName) );
	}
	
	public boolean validate(KraClass subClass){
				
		if(getSuperClass() == null)
			return false;
		this.setMethodCalled(getSuperClass().getMethod(getMessageName())); 
		if(this.getMethodCalled() != null)
			return true;
		
		return false;
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

	public KraClass getSuperClass() {
		return superClass;
	}

	public void setSuperClass(KraClass superClass) {
		this.superClass = superClass;
	}

	public Method getMethodCalled() {
		return methodCalled;
	}

	public void setMethodCalled(Method methodCalled) {
		this.methodCalled = methodCalled;
	}
    
}