package cyberwaste.kuzoff.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface FileSystemManager {

    Collection<File> getSubdirectories(File path);

    File getUserHome();

    void mkdirs(File path) throws IOException;

    File mkdir(File parent, String name) throws IOException;

    void rmr(File parent, String name) throws IOException;

    void rmr(File path) throws IOException;

    void writeToFile(File directory, String fileName, String data) throws IOException;

    String readFromFile(File directory, String name) throws IOException;

    void appendToFile(File directory, String name, String data) throws IOException;

    void mv(File directory, String from, String to) throws IOException;

    void rm(File directory, String name);

    void touch(File directory, String name) throws IOException;
}
