package ast;

public class ObjectExpr extends Expr {
	
    private KraClass kraClass;
    private boolean isNew;
    
    public ObjectExpr (KraClass kraClass, boolean isNew){
        this.kraClass = kraClass;
        this.isNew = isNew;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {

    }

    @Override
    public Type getType() {
        return kraClass;
    }

/*
 * 
 * new KraClass()
 * 
 */
	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if(putParenthesis)
			pw.print("(");
		
		if(this.isNew){
			pw.print("new ");
			pw.print(this.kraClass.getName());
			pw.print("()");
		}
		else
			pw.print("this");
		if(putParenthesis)
			pw.print(")");
	}
}
