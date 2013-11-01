package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

public class Row implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Value[] values;
    
    public Row(Value[] values) {
        this.values = (Value[]) ArrayUtils.clone(values);
    }

    public Value[] getValues() {
        return (Value[]) ArrayUtils.clone(values);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Row)) return false;
        
        Row other = (Row) obj;
        
        return Arrays.deepEquals(this.values, other.values);
    }
    
    @Override
    public int hashCode() {
        return 31 + Arrays.deepHashCode(values);
    }
}
