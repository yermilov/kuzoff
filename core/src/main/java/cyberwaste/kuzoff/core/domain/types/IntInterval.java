package cyberwaste.kuzoff.core.domain.types;

import org.apache.commons.lang.builder.EqualsBuilder;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class IntInterval extends Type {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Value value(String stringValue) {
        String[] tokens = stringValue.split(";");
        if (tokens.length != 2) {
            throw new IllegalArgumentException();
        }
        
        final IntIntervalObject value = new IntIntervalObject();
        value.lower = Integer.parseInt(tokens[0]);
        value.upper = Integer.parseInt(tokens[1]);
        if (value.lower > value.upper) {
            throw new IllegalArgumentException();
        }
        
        return new Value(this, "[" + value.toString() + "]", stringValue, value);
    }
    
    private static class IntIntervalObject {
        private int lower;
        private int upper;
        
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
        
        @Override
        public String toString() {
            return lower + ";" + upper;
        }
    }
}
