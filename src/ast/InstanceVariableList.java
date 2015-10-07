package ast;

import java.util.*;

public class InstanceVariableList {

    public InstanceVariableList() {
       instanceVariableList = new ArrayList<InstanceVariable>();
    }

    public void addElement(InstanceVariable instanceVariable) {
       instanceVariableList.add( instanceVariable );
    }

    public Iterator<InstanceVariable> elements() {
    	return this.instanceVariableList.iterator();
    }

    public int getSize() {
        return instanceVariableList.size();
    }

    public Variable searchVariable(Variable variable){
    	for(Variable v: instanceVariableList){
    		if(v.equals(variable))
    			return v;
    	}
    	return null;
    		
    }
    
    private ArrayList<InstanceVariable> instanceVariableList;

}
