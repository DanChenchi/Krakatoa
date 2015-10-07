package ast;

public class ObjectExpr extends Expr {

	private KraClass kraClass; 
	
	public ObjectExpr() {
		this.kraClass = new KraClass("Object", false, false);
	}
	
	public ObjectExpr(KraClass kraClass){
		this.kraClass = kraClass;
	}
	
	@Override
	public void genC(PW pw, boolean putParenthesis) {
		// TODO Auto-generated method stub
	}

	@Override
	public Type getType() {
		return Type.kraClass;
	}

	public KraClass getKraClass() {
		return kraClass;
	}

	public void setKraClass(KraClass kraClass) {
		this.kraClass = kraClass;
	}

}
