package cyberwaste.kuzoff.core.domain;

public abstract class Value {
    
    private final Type type;
    
    public Value(Type type) {
        this.type = type;
    }
    
    protected abstract String asString();
    
    @Override
    public String toString() {
        return asString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Value)) return false;
        
        Value other = (Value) obj;
        
        return this.type.equals(other.type) && this.asString().equals(other.asString());
    }
}
