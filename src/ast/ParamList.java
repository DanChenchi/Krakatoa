package ast;

import java.util.*;

public class ParamList {

    private ArrayList<Variable> paramList;

    public String getParamListNames() {
        String names = "";
        int i = paramList.size()-1;
        for (Variable variable: this.paramList){
            names = names + variable.getType().getName();
            if (i > 0){
                names = names + ", ";
                i--;
            }
        }
        return names;
    }

    public ParamList() {
       paramList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       paramList.add(v);
    }

    public Iterator<Variable> elements() {
        return paramList.iterator();
    }

    public int getSize() {
        return paramList.size();
    }

    public ArrayList<Variable> getParamList() {
        return paramList;
    }

    public TypeList getTypeList(){
        TypeList typeList = new TypeList();
        for (Variable variable: this.paramList){
            typeList.addElement(variable.getType());
        }
        return typeList;
    }

	public void genKra(PW pw) {
/*
 * Type name, Type name, Type name		
 */
		
		for (Iterator<Variable> iterator = paramList.iterator(); iterator.hasNext();) {
			Variable variable = (Variable) iterator.next();
			pw.print(variable.getType().getName());
			pw.print(" ");
			pw.print(variable.getName());
			if(iterator.hasNext())
				pw.print(", ");
		}
	}
}
