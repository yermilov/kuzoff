package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class Table implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final Type[] types;
    
    public Table(String name) {
        this(name, new Type[0]);
    }
    
    public Table(String name, Type[] types){
        this.name = name;
        
        this.types = new Type[types.length];
        System.arraycopy(types, 0, this.types, 0, types.length);
    }

    public String getName() {
        return name;
    }

    public Type[] getColumnTypes() {
        Type[] typesForReturn = new Type[types.length];
        System.arraycopy(types, 0, typesForReturn, 0, types.length);
        return typesForReturn;
    }
}
