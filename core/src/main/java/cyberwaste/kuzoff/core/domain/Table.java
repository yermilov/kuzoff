package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;

public class Table implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final Type[] types;
    
    public Table(String name) {
        this(name, new Type[0]);
    }
    
    public Table(String name, Type[] types){
        this.name = name;
        
        this.types = (Type[]) ArrayUtils.clone(types);
    }

    public String getName() {
        return name;
    }

    public Type[] getColumnTypes() {
        return (Type[]) ArrayUtils.clone(types);
    }
}
