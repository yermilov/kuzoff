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
    public File mkdir(File parent, String name) throws IOException {
        File directory = new File(parent, name);
        FileUtils.forceMkdir(directory);
        
        return directory;
    }
    
    @Override
    public void rmr(File parent, String name) throws IOException {
        FileUtils.forceDelete(new File(parent, name));
    }
    
    @Override
    public void rmr(File path) throws IOException {
        FileUtils.forceDelete(path);
    }
    
    @Override
    public void writeToFile(File directory, String fileName, String data) throws IOException {
        FileUtils.writeStringToFile(new File(directory, fileName), data);
    }
    
    @Override
    public String readFromFile(File directory, String name) throws IOException {
        return FileUtils.readFileToString(new File(directory, name));
    }
    
    @Override
    public void appendToFile(File directory, String name, String data) throws IOException {
        FileUtils.write(new File(directory, name), data + "\n", true);
    }
    
    @Override
    public void mv(File directory, String from, String to) throws IOException {
        FileUtils.copyFile(new File(directory, from), new File(directory, to));
        FileUtils.deleteQuietly(new File(directory, from));
    }
    
    @Override
    public void rm(File directory, String name) {
        FileUtils.deleteQuietly(new File(directory, name));
    }
    
    @Override
    public void touch(File directory, String name) throws IOException {
        FileUtils.touch(new File(directory, name));
    }
}
