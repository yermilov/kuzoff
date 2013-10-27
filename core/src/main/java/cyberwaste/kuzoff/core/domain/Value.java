package cyberwaste.kuzoff.core.domain;

public abstract class Value {
    
    protected abstract String asString();
    
    @Override
    public String toString() {
        return asString();
    }
}
