package cyberwaste.kuzoff.core.domain.types;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class Real extends Type {

    @Override
    protected String getName() {
        return "real";
    }

    @Override
    public Value value(String stringValue) {
        final double value = Double.parseDouble(stringValue);
        return new Value(this) {
            
            @Override
            protected String asString() {
                return Double.toString(value);
            }
        };
    }
}
