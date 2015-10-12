package ast;

public class MessageSendToSuper extends MessageSend { 

    private ExprList exprList;
    private String name;
    private KraClass classOfMethod;

    public String getExprListNames() {
        return exprList.getTypeNames();
    }

    public MessageSendToSuper (ExprList exprList, String name){
        if (exprList == null){
            this.exprList = new ExprList();
        }else{
            this.exprList = exprList;
        }
        this.name = name;
        this.classOfMethod = null;
    }

    public boolean validate(KraClass kraClass){
        KraClass superClass = kraClass.getSuperclass();
        if (superClass != null)
            this.classOfMethod = superClass.searchMethods(name, exprList.getTypeList(), false ,true);
        if (this.classOfMethod != null){
            return true;
        }
        return false;
    }


    public Type getType() { 
        return null;
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }
    
}