package cyberwaste.kuzoff.core.domain.types;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class Char extends Type {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Value value(String stringValue) {
        if (stringValue.length() != 1) {
            throw new IllegalArgumentException();
        }
        
        return new Value(this, stringValue, stringValue, stringValue.charAt(0));
    }
}
