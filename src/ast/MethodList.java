package ast;

import java.util.ArrayList;

/**
 * Created by joao on 29/09/15.
 */
public class MethodList {

    public Method searchNameMethod(String run){
        for(Method method : methodList){
            if (method.getName().equals(run)) {
                return method;
            }
        }
        return null;
    }

    public Method search(String name, TypeList typeList, boolean isStatic){
        for(Method method : methodList){
            if (method.getName().equals(name)) {
                if (isStatic && method.isStatic()){
                    return method;
                } else if (typeList.compareTo(method.getParamList().getTypeList()) == 0) {
                    if (method.isStatic() == isStatic){
                        return method;
                    }
                }
            }
        }
        return null;
    }

    public Method searchDeclaration(Type type, String name, TypeList typeList, boolean isStatic, boolean superClass){
        for(Method method : methodList){
            if (method.getName().equals(name)) {
                if (superClass){
                    if(typeList.compareTo(method.getParamList().getTypeList()) != 0){
                        return method;
                    }
                    if (method.getType() != type){
                        return method;
                    }
                    if (method.isFinal()){
                        return method;
                    }
                }
                if (isStatic && method.isStatic()){
                    return method;
                } else if (typeList.compareTo(method.getParamList().getTypeList()) == 0) {
                    if (method.isStatic() == isStatic && !superClass){
                        return method;
                    }else if (method.isStatic() == isStatic ){
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public MethodList() {
        methodList = new ArrayList<Method>();
    }

    public void addElement(Method method) {
        methodList.add(method);
    }

    public void addMethodList(MethodList methodList) {
        this.methodList.addAll(methodList.getMethodList());
    }

    public int getSize() {
        return methodList.size();
    }

    public ArrayList<Method> getMethodList() {
        return methodList;
    }
    
    public void genKra(PW pw, String scope){
    	for(Method m: methodList)
    		m.genKra(pw, scope);
    }

    private ArrayList<Method> methodList;
}
