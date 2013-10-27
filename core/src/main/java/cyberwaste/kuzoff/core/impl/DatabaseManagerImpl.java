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
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public class DatabaseManagerImpl implements DatabaseManager {
    
    private static final String METADATA_FILE = ".metadata";
    private static final String DATA_FILE = "data";
    
    private FileSystemManager fileSystemManager;
    private File kuzoffHome;
    
    @PostConstruct
    public void init() {
        try {
            kuzoffHome = new File(fileSystemManager.getUserHome(), "kuzoff");
            fileSystemManager.mkdirs(kuzoffHome);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't initialize kuzoff root directory", e);
        }
    }

    @Override
    public Collection<Table> getAllTables() {
        try {
            Collection<File> tableDirectories = fileSystemManager.getSubdirectories(kuzoffHome);
            
            Collection<Table> tables = new ArrayList<>();
            for (File tableDirectory : tableDirectories) {
                tables.add(tableFromDirectory(tableDirectory));
            }
            return tables;
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't get tables", e);
        }
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
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't create table", e);
        }
    }
    
    @Override
    public Table removeTable(String name) {
        try {
            fileSystemManager.rmr(kuzoffHome, name);
            return new Table(name);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't remove table", e);
        }
    }
    
    @Override
    public void removeDatabase() {
        try {
            fileSystemManager.rmr(kuzoffHome);
            fileSystemManager.mkdirs(kuzoffHome);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't remove databases", e);
        }
    }
    
    @Override
    public Table getTable(String name) {
        try {
            return tableFromDirectory(new File(kuzoffHome, name));
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't get table " + name, e);
        }
    }
    
    @Override
    public Row insertRow(String tableName, String[] stringValues) {
        try {
            Table table = getTable(tableName);
            
            Type[] types = table.getColumnTypes();
            Value[] values = new Value[stringValues.length];
            
            if (types.length != values.length) {
                throw new IllegalArgumentException("Table " + tableName + " has " + types.length + " row(s), but " + values.length + " value(s) was(were) passed");
            }
            
            for (int i = 0; i < stringValues.length; i++) {
                values[i] = types[i].value(stringValues[i]);
            }
            
            String rowData = StringUtils.join(values, "|");
            
            fileSystemManager.appendToFile(new File(kuzoffHome, tableName), DATA_FILE, rowData);
            
            return new Row(values);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't insert row into table " + tableName, e);
        }
    }

    private Table tableFromDirectory(File tableDirectory) throws IOException {
        String tableName = tableDirectory.getName();
        String metadata = fileSystemManager.readFromFile(tableDirectory, METADATA_FILE);
        
        String[] columnTypes = StringUtils.split(metadata, '|');
        Type[] types = new Type[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            types[i] = Type.getType(columnTypes[i]);
        }
        
        return new Table(tableName, types);
    }
    
    public void setFileSystemManager(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }
}
