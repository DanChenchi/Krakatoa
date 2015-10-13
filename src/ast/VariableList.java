package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class VariableList {

    public VariableList() {
        variableList = new ArrayList<Variable>();
    }

    public VariableList(ArrayList<Variable> variableList) {
        this.variableList = variableList;
    }

    public void addElement(Variable variable) {
        variableList.add(variable);
    }

    public void addList(VariableList variableList){
        this.variableList.addAll(variableList.getVariableList());
    }

    public Iterator<Variable> elements() {
        return this.variableList.iterator();
    }

    public int getSize() {
        return variableList.size();
    }

    public ArrayList<Variable> getVariableList() {
        return variableList;
    }

    private ArrayList<Variable> variableList;
    
    public void genKra(PW pw){
    	
    	for (int i = 0; i < this.variableList.size(); i++) {
			Variable variable = this.variableList.get(i);
			pw.print(variable.getName());
			if(!( i == (this.variableList.size() - 1) ) ) //if it's not the last element
				pw.print(", ");
				
		}
    }
}
