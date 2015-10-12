package ast;


public class MessageSendToVariable extends MessageSend {

    private String firstId;
    private String identifier;
    private String messageName;
    private ExprList exprList;
    private KraClass kraClass;
    private Type instanceVariableType;
    private Type methodType;
    private KraClass methodClass;

    public String getFirstId() {
        return firstId;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public String getIdentifier() {
        return identifier;
    }

    // Clock.currentDay;
    public MessageSendToVariable (String firstId, String identifier){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = null;
        this.exprList = new ExprList();
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
    }

    public boolean validateInstance (KraClass kraClass){
        this.kraClass = kraClass;
        this.instanceVariableType =  this.kraClass.getInstanceVariableList().searchStaticInstance(this.identifier);
        return (instanceVariableType != null);
    }

    // object.setDay(12);
    public MessageSendToVariable (String firstId, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = null;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
    }

    // Clock.currentDay.setDay(12);
    public MessageSendToVariable (String firstId, String identifier, String messageName, ExprList exprList){
        this.firstId = firstId;
        this.identifier = identifier;
        this.messageName = messageName;
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.instanceVariableType = null;
        this.methodType = null;
        this.methodClass = null;
    }

//    ok
    public boolean validateMethodMessage (KraClass kraClass, KraClass classFromVariable, Method currentMethod){

        if (classFromVariable != null){
            this.kraClass = classFromVariable;
            this.methodClass = this.kraClass.searchMethods(this.messageName, this.exprList.getTypeList(), false, true);
            if ( methodClass != null) {
                methodType = this.methodClass.getMethodType(this.messageName, this.exprList.getTypeList(),true);
                return true;
            }else{
                if (this.kraClass.compareCurrentMethod(this.messageName,this.exprList.getTypeList(),false,currentMethod)){
                    methodType = currentMethod.getType();
                    return true;
                }
                return false;
            }
        }else if (kraClass != null) {
            this.kraClass = kraClass;
            this.methodClass = this.kraClass.searchMethods(this.messageName, this.exprList.getTypeList(), true, true);
            if (methodClass != null) {
                methodType = this.methodClass.getMethodType(this.messageName, this.exprList.getTypeList(), true);
                return true;
            } else {
                if (this.kraClass.compareCurrentMethod(this.messageName, this.exprList.getTypeList(), false, currentMethod)) {
                    methodType = currentMethod.getType();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean validateIndentifierMessage (){
        KraClass superClass = kraClass.searchMethods(messageName, exprList.getTypeList(), false, false);
        if (superClass != kraClass && superClass != null){
            this.methodClass = kraClass;
            methodType = superClass.getMethodType(messageName,exprList.getTypeList(),true);
        }else if (superClass != null){
            methodType = kraClass.getMethodType(messageName,exprList.getTypeList(),true);
        }else{
            return false;
        }
        return true;
    }

    public Type getType() {
        if (methodType != null){
            return this.methodType;
        }else{
            return this.instanceVariableType;
        }
    }

    public void genKra(){}
    
    public void genC( PW pw, boolean putParenthesis ) {
        
    }

    
}    