package cyberwaste.kuzoff.core.domain.types;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class HtmlFile extends Type {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Value value(String stringValue) {
        try {
            File value = new File(stringValue);
            return new Value(this, Jsoup.parse(FileUtils.readFileToString(value)).html(), value.getAbsolutePath(), value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
