package ast;

/**
 * Created by joao on 07/10/15.
 */
public class TypeNull extends Type {

    public TypeNull() {
        super("null");
    }

    public String getCname() {
        return "null";
    }

}
