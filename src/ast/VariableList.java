package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by joao on 29/09/15.
 */
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
}
