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
        final int value = Integer.parseInt(stringValue);
        return new Value(this){

            @Override
            protected String asString() {
                return Integer.toString(value);
            }
        };
    }
}
