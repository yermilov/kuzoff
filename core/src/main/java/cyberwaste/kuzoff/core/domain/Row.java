package cyberwaste.kuzoff.core.domain;

import org.apache.commons.lang.ArrayUtils;

public class Row {
    
    private final Value[] values;
    
    public Row(Value[] values) {
        this.values = (Value[]) ArrayUtils.clone(values);
    }

    public Value[] getValues() {
        return (Value[]) ArrayUtils.clone(values);
    }
}
