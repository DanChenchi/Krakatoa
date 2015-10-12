package ast;

public class NullExpr extends Expr {
    
   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NULL");
   }

   public Type getType() {
      return type;
   }

   public NullExpr (){
      this.type = Type.nullType;
   }

   private Type type;
}