package cyberwaste.kuzoff.core.domain.types;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class Int extends Type {
    
    @Override
    public String getName() {
        return "int";
    }
    
    @Override
    public Value value(String stringValue) {
        final Integer value = Integer.parseInt(stringValue);
        return new Value(this){
            
            @Override
            public String externalStringRepresentation() {
                return Integer.toString(value);
            }

            @Override
            protected String internalStringRepresentation() {
                return Integer.toString(value);
            }

            @Override
            protected Integer asObject() {
                return value;
            }
        };
    }
}
