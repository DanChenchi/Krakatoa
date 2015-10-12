package ast;

import java.util.*;

public class ExprList{

    private ArrayList<Expr> exprList;

    public ExprList() {
        this.exprList = new ArrayList<Expr>();
    }

    public void addElement( Expr expr ) {
        exprList.add(expr);
    }

    public boolean checkWriteExprListForBoolean(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.booleanType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForNull(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.nullType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForVoid(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.voidType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForUndefined(){
        for (Expr expr: this.exprList){
            Type type = expr.getType();
            if (type == Type.undefinedType){
                return false;
            }
        }
        return true;
    }

    public boolean checkWriteExprListForObject(){
        for (Expr expr: this.exprList){
            if (expr instanceof VariableExpr && expr.getType() instanceof KraClass){
                return false;
            }
        }
        return true;
    }

    public String getTypeNames(){
        String names = "";
        int i = exprList.size()-1;
        for (Expr expr: this.exprList){
            names = names + expr.getType().getName();
            if (i > 0){
                names = names + ", ";
                i--;
            }
        }
        return names;
    }

    public boolean checkReadExprList(){
        for (Expr expr: this.exprList){
            if ((!(expr instanceof VariableExpr)) &&
            ((expr instanceof MessageSendToSelf && ((MessageSendToSelf) expr).getMessageName() == null))) {
                return true;
            }else if (expr instanceof MessageSendToVariable && ((MessageSendToVariable) expr).getMessageName() == null){
                return true;
            }else if ((expr.getType() instanceof KraClass) || expr.getType() == Type.voidType || expr.getType() == Type.booleanType || expr.getType() == Type.nullType || expr.getType() == Type.undefinedType){
                return false;
            }
        }
        return true;
    }

    public void genC( PW pw ) {

        int size = exprList.size();
        for ( Expr e : exprList ) {
        	e.genC(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Expr expr: this.exprList){
            typeList.addElement(expr.getType());
        }
        return typeList;
    }
}
