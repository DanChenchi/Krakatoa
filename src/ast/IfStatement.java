package ast;

public class IfStatement extends Statement {

    private Expr expr;
    private Statement ifStatement;
    private Statement elseStatement;

    public IfStatement(Expr expr, Statement ifStatement, Statement elseStatement){
        this.setExpr(expr);
        this.setIfStatement(ifStatement);
        this.setElseStatement(elseStatement);
    }

    public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public Statement getIfStatement() {
		return ifStatement;
	}

	public void setIfStatement(Statement ifStatement) {
		this.ifStatement = ifStatement;
	}

	public Statement getElseStatement() {
		return elseStatement;
	}

	public void setElseStatement(Statement elseStatement) {
		this.elseStatement = elseStatement;
	}
	
/*
 * if( expr )
 * 		ifStatement
 * else
 * 		elseStatement
 * 
 * 
 * OBS: as chaves {} são inseridas se ifStatement ou elseStatement forem do tipo CompositeStatement
 * Nesse caso fica
 * 
 * if( expr ) {
 * 		Statement
 * 		Statement
 * }
 * else {
 * 		Statement
 * 		Statement
 * }
 */
	@Override
	public void genKra(PW pw){
		pw.printIdent("if( ");
		this.expr.genKra(pw, false);
				
		if(this.ifStatement instanceof CompositeStatement){
			pw.print(" ) ");
			this.ifStatement.genKra(pw);
		}
		else{
			pw.println(" )");
			pw.add();
			if(this.ifStatement != null)
				this.ifStatement.genKra(pw);
			pw.sub();
		}
		
		if(this.elseStatement != null){
			pw.printIdent("else ");
			
			if(this.elseStatement instanceof CompositeStatement){
				this.elseStatement.genKra(pw);
			}
			else{
				pw.println("");
				pw.add();
				this.elseStatement.genKra(pw);
				pw.sub();
			}
		}
    }

    @Override
    public void genC(PW pw) {

    }
}
