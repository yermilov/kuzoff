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
    
    public abstract String getName();
    
    @Override
    public String toString() {
        return getName();
    }
}
