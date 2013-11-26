package cyberwaste.kuzoff.core.domain.types;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class TextFile extends Type {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Value value(String stringValue) {
        try {
            File value = new File(stringValue);
            return new Value(this, FileUtils.readFileToString(value), value.getAbsolutePath(), value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
