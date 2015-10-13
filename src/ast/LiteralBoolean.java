package ast;

public class LiteralBoolean extends Expr {

    public LiteralBoolean( boolean value ) {
        this.value = value;
    }

    @Override
	public void genC( PW pw, boolean putParenthesis ) {
       pw.print( value ? "1" : "0" );
    }

    @Override
	public Type getType() {
        return Type.booleanType;
    }

    public static LiteralBoolean True  = new LiteralBoolean(true);
    public static LiteralBoolean False = new LiteralBoolean(false);

    private boolean value;

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		if(putParenthesis)
			pw.print("(");
		
		if(this.value)
			pw.print("true");
		else
			pw.print("false");
		
		if(putParenthesis)
			pw.print(")");
	}
}
