package ast;

/**
 * Created by joao on 29/09/15.
 */
public class WhileStatement extends Statement {

    private Expr expr;
    private Statement statement;

    public WhileStatement(Expr expr, Statement statement){
        this.expr = expr;
        this.statement = statement;
    }

/*
 * while( expr )
 * 		Statement
 * 
 * OBS: caso o statement seja um CompositeStatement as chaves {} são colocadas da seguinte maneira:
 * 
 * while( expr ) {
 * 		Statement
 * 		Statement
 * 		Statement
 * }
 * 
 */
    
    @Override
    public void genKra (PW pw){
    	pw.printIdent("while( ");
    	this.expr.genKra(pw, false);
    	
    	if(this.statement instanceof CompositeStatement){
    		pw.print(" ) ");
    		this.statement.genKra(pw);
    	}
    	else{
    		pw.println(" )");
    		pw.add();
    		if(this.statement != null)
    			this.statement.genKra(pw);
    		pw.sub();
    	}
    	
    }

    @Override
    public void genC(PW pw) {

    }
}
