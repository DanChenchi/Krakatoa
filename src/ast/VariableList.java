package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class VariableList {

	 public VariableList() {
	       variableList = new ArrayList<Variable>();
	    }

	    public void addElement(Variable variable) {
	       variableList.add( variable );
	    }

	    public Iterator<Variable> elements() {
	    	return this.variableList.iterator();
	    }

	    public int getSize() {
	        return variableList.size();
	    }

	    private ArrayList<Variable> variableList;

	
	
}
