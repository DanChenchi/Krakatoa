package ast;

/**
 * Created by joao on 30/09/15.
 */
public class AssignmentStatement extends Statement {

    private Expr leftExpr, rightExpr;

    public AssignmentStatement (Expr leftExpr, Expr rightExpr){
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }


    public void genKra(PW pw){

    }

    @Override
    public void genC(PW pw) {

    }
}
