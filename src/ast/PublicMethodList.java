package ast;

import java.util.ArrayList;

public class PublicMethodList {
	private ArrayList<PublicMethod> list;
	
	public PublicMethodList(){
		this.setList(new ArrayList<PublicMethod>());
	}
	
	public PublicMethodList(ArrayList<PublicMethod> list){
		this.setList(list);
	}

	public ArrayList<PublicMethod> getList() {
		return list;
	}

	public void setList(ArrayList<PublicMethod> list) {
		this.list = list;
	}
	
	public void addElement(Method method){
		this.list.add(new PublicMethod(method.getName(), method.getParameters(), method.getType(), method.isStatic(), method.isFinal()));
	}
	
	
	public Method search(String methodName){
		for(Method m: list){
			if(m.getName().equals(methodName))
				return m;
		}
		return null;
	}
}
