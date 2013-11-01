package cyberwaste.kuzoff.core.domain;

import org.apache.commons.lang.StringUtils;

public abstract class Type {

    public static Type getType(String typeName) {
        try {
            String typeClassName = "cyberwaste.kuzoff.core.domain.types." + StringUtils.capitalize(typeName);
            Class<?> typeClass = Class.forName(typeClassName);
            return (Type) typeClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Unknown type: " + typeName);
        }
    }

    public abstract Value value(String stringValue);
    
    public String getName() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Type)) return false;
        
        Type other = (Type) obj;
        
        return this.getName().equals(other.getName());
    }
}
