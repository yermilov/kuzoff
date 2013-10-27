package cyberwaste.kuzoff.core.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.DatabaseManagerException;
import cyberwaste.kuzoff.core.FileSystemManager;
import cyberwaste.kuzoff.core.domain.Table;

public class DatabaseManagerImpl implements DatabaseManager {
    
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
    public Table createTable(String name) {
        try {
            fileSystemManager.mkdir(kuzoffHome, name);
            return new Table(name);
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

    private Table tableFromDirectory(File tableDirectory) {
        return new Table(tableDirectory.getName());
    }
    
    public void setFileSystemManager(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }
}
