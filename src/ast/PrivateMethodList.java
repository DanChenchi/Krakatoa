package ast;

import java.util.ArrayList;

public class PrivateMethodList {
	private ArrayList<PrivateMethod> list;
	
	public PrivateMethodList(){
		this.list = new ArrayList<PrivateMethod>();
	}
	
	public PrivateMethodList(ArrayList<PrivateMethod> list){
		this.list = list;
	}

	public ArrayList<PrivateMethod> getList() {
		return this.list;
	}

	public void setList(ArrayList<PrivateMethod> list) {
		this.list = list;
	}
	
	public void addElement(Method method){
		this.list.add(new PrivateMethod(method.getName(), method.getParameters(), method.getType(), method.isStatic(), method.isFinal()));
	}
	
	public Method search(String methodName){
		for(Method m: list){
			if(m.getName().equals(methodName))
				return m;
		}
		return null;
	}
}
