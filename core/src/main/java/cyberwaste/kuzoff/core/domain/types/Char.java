package cyberwaste.kuzoff.core.domain.types;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class Char extends Type {

    @Override
    public Value value(String stringValue) {
        if (stringValue.length() != 1) {
            throw new IllegalArgumentException();
        }
        
        final Character value = stringValue.charAt(0);
        return new Value(this) {
            
            @Override
            public String externalStringRepresentation() {
                return Character.toString(value);
            }
            
            @Override
            protected String internalStringRepresentation() {
                return Character.toString(value);
            }

            @Override
            protected Character asObject() {
                return value;
            }
        };
    }
}
