package cyberwaste.kuzoff.core.domain.types;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class Int extends Type {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public String getName() {
        return "int";
    }
    
    @Override
    public Value value(String stringValue) {
        return new Value(this, stringValue, stringValue, Integer.parseInt(stringValue));
    }
}
