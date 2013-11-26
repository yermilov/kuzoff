package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class Table implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String[] typeNames;
    
    public Table() { }
    
    public Table(String name) {
        this(name, new Type[0]);
    }
    
    public Table(String name, Type[] types){
        this.name = name;
        
        this.typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            this.typeNames[i] = types[i].getName();
        }
    }

    public Type[] columnTypes() {
        Type[] columnTypes = new Type[typeNames.length];
        for (int i = 0; i < typeNames.length; i++) {
            columnTypes[i] = Type.getType(typeNames[i]);
        }
        return columnTypes;
    }

    public String getName() {
        return name;
    }
    
    public String[] getTypeNames() {
        return typeNames;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setTypeNames(String[] typeNames) {
        this.typeNames = typeNames;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Table)) return false;
        
        Table other = (Table) obj;
        
        return this.name.equals(other.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
