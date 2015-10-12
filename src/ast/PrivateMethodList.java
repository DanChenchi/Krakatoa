package ast;

public class PrivateMethodList extends MethodList {

    public PrivateMethodList(){
        super();
    }
    
	public void genKra(PW pw) {
		for(Method m : this.getMethodList()){
			m.genKra(pw, "private");
		}
	}
}
