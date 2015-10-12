package ast;

import java.util.ArrayList;

/**
 * Created by joao on 30/09/15.
 */
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

    public Type getPosition(int i){
        return this.typeList.get(i);
    }

    public ArrayList<Type> getTypeList() {
        return typeList;
    }

    @Override
    public int compareTo(TypeList typeListParameter) {
        if (this.getSize() != typeListParameter.getSize()){
            return 1;
        }else{
            if (typeListParameter.getSize() == 0){
                return 0;
            }else{
                int i = 0;
                for (Type type: typeList){
                    if (!(type.getName().equals(typeListParameter.getPosition(i).getName()))){
                        if (type instanceof KraClass && (!((KraClass) type).searchSuperClassName(typeListParameter.getPosition(i).getName()))){
                            return 1;
                        } else if (!(type instanceof KraClass)){
                            return 1;
                        }
                    }
                    i++;
                }
                return 0;
            }
        }
    }
}
