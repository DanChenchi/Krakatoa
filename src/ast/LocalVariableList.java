package ast;

import java.util.*;

public class LocalVariableList {

    public LocalVariableList() {
       localList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       localList.add(v);
    }

    public Iterator<Variable> elements() {
        return localList.iterator();
    }

    public int getSize() {
        return localList.size();
    }

    private ArrayList<Variable> localList;

	public void genKra(PW pw) {
/*
 * Type name;
 * Type name;
 * Type name;
 */
		for (Variable variable : localList) {
			pw.printIdent(variable.getType().getName());
			pw.print(" ");
			pw.print(variable.getName());
			pw.println(";");
		}
		pw.println("");
	}

}
