package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class Value implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String typeName;
    protected String valueAsString;
    protected String valueAsInnerString;
    protected Object object;
    
    public Value() { }
    
    public Value(Type type, String valueAsString, String valueAsInnerString, Object object) {
        this.typeName = type.getName();
        this.valueAsString = valueAsString;
        this.valueAsInnerString = valueAsInnerString;
        this.object = object;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getValueAsString() {
        return valueAsString;
    }
    
    public void setValueAsString(String valueAsString) {
        this.valueAsString = valueAsString;
    }
    
    public String getValueAsInnerString() {
        return valueAsInnerString;
    }
    
    public void setValueAsInnerString(String valueAsInnerString) {
        this.valueAsInnerString = valueAsInnerString;
    }
    
    public Object getObject() {
        return object;
    }
    
    public void setObject(Object object) {
        this.object = object;
    }
    
    @Override
    public String toString() {
        return getValueAsInnerString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Value)) return false;
        
        Value other = (Value) obj;
        
        return Type.getType(this.typeName).equals(Type.getType(other.typeName)) && this.getObject().equals(other.getObject());
    }
    
    @Override
    public int hashCode() {
        return getObject().hashCode();
    }
}
