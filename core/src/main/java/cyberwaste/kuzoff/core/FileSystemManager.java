package cyberwaste.kuzoff.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface FileSystemManager {

    Collection<File> getSubdirectories(File path);

    File getUserHome();

    void mkdirs(File path) throws IOException;

    void mkdir(File parent, String name) throws IOException;

    void rmr(File parent, String name) throws IOException;

    void rmr(File path) throws IOException;
}
