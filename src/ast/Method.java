package ast;

/**
 * Created by joao on 29/09/15.
 */
public class Method {

    private Type type;
    private String name;
    private boolean isStatic;
    private boolean isFinal;
    private ParamList paramList;
    private LocalVariableList localVariableList;
    private StatementList statementList;
    private boolean hasReturn;

    public Method(Type type, String name, boolean isStatic, boolean isFinal, ParamList paramList){
        this.type = type;
        this.name = name;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.paramList = paramList;
        this.statementList = null;
        this.hasReturn = false;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean checkReturnType(Statement statement){
        if (statement instanceof ReturnStatement && ((ReturnStatement) statement).getReturnType().getName().equals(this.type.getName())){
            hasReturn = true;
            return true;
        }else if (statement instanceof ReturnStatement && ((ReturnStatement) statement).getReturnType() instanceof KraClass){
            KraClass kraClass = ((KraClass)((ReturnStatement) statement).getReturnType()).getSuperclass();
            return (verifySuperClassType(kraClass,this.type.getName()));
        }
        return false;
    }

    public boolean verifySuperClassType(KraClass kraClass, String name){
        if (kraClass == null){
            return false;
        }
        else if (kraClass.getName().equals(name)){
            return true;
        }else if (kraClass.getSuperclass() != null){
            return verifySuperClassType(kraClass.getSuperclass(),name);
        }else{
            return false;
        }
    }

    public boolean hasReturn() {
        return hasReturn;
    }

    public String getName() {
        return name;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public Type getType(){
        return this.type;
    }

    public void setStatementList(StatementList statementList) {
        this.statementList = statementList;
    }

	public void genKra(PW pw, String scope) {
/*		
 * final static Type name( paramList ) {
 * 		localVariableList
 * 		statementList
 * }
*/		pw.printIdent("");	
		if(this.isFinal)
			pw.print("final ");
		if(this.isStatic)
			pw.print("static ");
		
		pw.print(scope);
		pw.print(" ");
		pw.print(this.type.getName());
		pw.print(" ");
		pw.print(this.name);
		pw.print("( ");
		
		if(this.paramList != null)
			this.paramList.genKra(pw);
		
		pw.println(") {");
		pw.add();
		
		if(this.localVariableList != null)
			this.localVariableList.genKra(pw);
		
		if(this.statementList != null)
			this.statementList.genKra(pw);
		pw.sub();
		pw.printlnIdent("}");
		
	}
}
