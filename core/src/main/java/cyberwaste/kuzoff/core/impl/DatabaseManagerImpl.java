package cyberwaste.kuzoff.core.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.DatabaseManagerException;
import cyberwaste.kuzoff.core.FileSystemManager;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;

public class DatabaseManagerImpl implements DatabaseManager {
    
    private static final String METADATA_FILE = ".metadata";
    
    private FileSystemManager fileSystemManager;
    private File kuzoffHome;
    
    @PostConstruct
    public void init() {
        try {
            kuzoffHome = new File(fileSystemManager.getUserHome(), "kuzoff");
            fileSystemManager.mkdirs(kuzoffHome);
        } catch (IOException e) {
            throw new DatabaseManagerException("Can't initialize kuzoff root directory", e);
        }
    }

    @Override
    public Collection<Table> getAllTables() {
        Collection<File> tableDirectories = fileSystemManager.getSubdirectories(kuzoffHome);
        
        Collection<Table> tables = new ArrayList<>();
        for (File tableDirectory : tableDirectories) {
            tables.add(tableFromDirectory(tableDirectory));
        }
        return tables;
    }
    
    @Override
    public Table createTable(String name, String[] columnTypes) {
        try {
            Type[] types = new Type[columnTypes.length];
            for (int i = 0; i < columnTypes.length; i++) {
                types[i] = Type.getType(columnTypes[i]);
            }
            String metadata = StringUtils.join(types, '|');
            
            File tableDirectory = fileSystemManager.mkdir(kuzoffHome, name);
            fileSystemManager.writeToFile(tableDirectory, METADATA_FILE, metadata);
            
            return new Table(name, types);
        } catch (IOException e) {
            throw new DatabaseManagerException("Can't create table", e);
        }
    }
    
    @Override
    public Table removeTable(String name) {
        try {
            fileSystemManager.rmr(kuzoffHome, name);
            return new Table(name);
        } catch (IOException e) {
            throw new DatabaseManagerException("Can't remove table", e);
        }
    }
    
    @Override
    public void removeDatabase() {
        try {
            fileSystemManager.rmr(kuzoffHome);
            fileSystemManager.mkdirs(kuzoffHome);
        } catch (IOException e) {
            throw new DatabaseManagerException("Can't remove databases", e);
        }
    }
    
    @Override
    public Table getTable(String name) {
        return tableFromDirectory(new File(kuzoffHome, name));
    }

    private Table tableFromDirectory(File tableDirectory) {
        String tableName = tableDirectory.getName();
        try {
            String metadata = fileSystemManager.readFromFile(tableDirectory, METADATA_FILE);
            
            String[] columnTypes = StringUtils.split(metadata, '|');
            Type[] types = new Type[columnTypes.length];
            for (int i = 0; i < columnTypes.length; i++) {
                types[i] = Type.getType(columnTypes[i]);
            }
            
            return new Table(tableName, types);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't find table " + tableName, e);
        }
    }
    
    public void setFileSystemManager(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }
}
