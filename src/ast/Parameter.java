package ast;


public class Parameter extends Variable {

    public Parameter( String name, Type type ) {
        super(name, type);
    }
    
    public void genKra(PW pw){
    	pw.print(this.getName());
    }

}