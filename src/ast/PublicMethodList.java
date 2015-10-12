package ast;


public class PublicMethodList extends MethodList {

    public PublicMethodList(){
        super();
    }
    
    public void genKra(PW pw){
    	super.genKra(pw, "public");
    }
}
