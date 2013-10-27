package cyberwaste.kuzoff.core.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import cyberwaste.kuzoff.core.FileSystemManager;

public class FileSystemManagerImpl implements FileSystemManager {
    
    @Override
    public Collection<File> getSubdirectories(final File path) {
        return Arrays.asList(path.listFiles());
    }
    
    @Override
    public File getUserHome() {
        return FileUtils.getUserDirectory();
    }
    
    @Override
    public void mkdirs(File path) throws IOException {
        FileUtils.forceMkdir(path);
    }
    
    @Override
    public void mkdir(File parent, String name) throws IOException {
        FileUtils.forceMkdir(new File(parent, name));
    }
}
