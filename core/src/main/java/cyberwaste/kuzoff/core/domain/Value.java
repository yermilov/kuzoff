package cyberwaste.kuzoff.core.domain;

public abstract class Value {
    
    private final Type type;
    
    public Value(Type type) {
        this.type = type;
    }

    public abstract String externalStringRepresentation();
    
    protected abstract String internalStringRepresentation();
    protected abstract Object asObject();
    
    @Override
    public String toString() {
        return internalStringRepresentation();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Value)) return false;
        
        Value other = (Value) obj;
        
        return this.type.equals(other.type) && this.asObject().equals(other.asObject());
    }
    
    @Override
    public int hashCode() {
        return asObject().hashCode();
    }
}
