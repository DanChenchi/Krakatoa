package ast;


public class MessageSendToSelf extends MessageSend {

    private InstanceVariable instanceVariable;
    private KraClass instanceVariableClass;
    private KraClass currentClass;
    private String messageName;
    private ExprList exprList;
    private Method method;

    public Method getMethod() {
        return method;
    }

    public String getMessageName() {
        return messageName;
    }

    public InstanceVariable getInstanceVariable() {
        return instanceVariable;
    }

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = instanceVariable;
        this.messageName = null;
        this.exprList = new ExprList();
        this.method = null;
    }

    public MessageSendToSelf(KraClass currentClass, String messageName, ExprList exprList){
        this.currentClass = currentClass;
        this.instanceVariableClass = null;
        this.instanceVariable = null;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.method = null;
    }

    public MessageSendToSelf(KraClass currentClass, InstanceVariable instanceVariable, String messageName, ExprList exprList, KraClass instanceVariableClass){
        this.currentClass = currentClass;
        this.instanceVariableClass = instanceVariableClass;
        this.instanceVariable = instanceVariable;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.method = null;
    }

    public boolean validateInstanceMessage() {
        KraClass methodClass = this.instanceVariableClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,true);
        if (methodClass != null){
            this.method = methodClass.getMethod(this.messageName,exprList.getTypeList());
            return true;
        }
        return false;
    }

    public boolean validateClassMessage(Method currentMethod){
        KraClass methodClass = this.currentClass.searchMethods(this.messageName,this.exprList.getTypeList(),false,false);
        if (methodClass != null){
            this.method = methodClass.getMethodFromThis(this.messageName,exprList.getTypeList());
            return true;
        }else{
            if (this.currentClass.compareCurrentMethod(this.messageName,this.exprList.getTypeList(),false,currentMethod)){
                this.method = currentMethod;
                return true;
            }
        }
        return false;
    }

    public Type getType() {
        if (this.method != null )
            return this.method.getType();
        else
            return this.instanceVariable.getType();
    }

    private void genKra(PW pw){

    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }
    
    
}