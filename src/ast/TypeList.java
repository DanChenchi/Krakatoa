package ast;

import java.util.ArrayList;

public class TypeList implements Comparable<TypeList> {

    private ArrayList<Type> typeList;

    public TypeList() {
        typeList = new ArrayList<Type>();
    }

    public void addElement(Type type) {
        typeList.add(type);
    }

    public void addTypeList(TypeList typeList) {
        this.typeList.addAll(typeList.getTypeList());
    }

    public int getSize() {
        return typeList.size();
    }

    public ArrayList<Type> getTypeList() {
        return typeList;
    }

    @Override
    public int compareTo(TypeList typeList) {
        if (this == typeList){
            return 0;
        }else{
            return 1;
        }
    }
}