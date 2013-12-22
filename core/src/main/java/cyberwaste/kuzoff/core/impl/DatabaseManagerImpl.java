package cyberwaste.kuzoff.core.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    static final String METADATA_FILE = ".metadata";
    static final String DATA_FILE = "data";
    private static final String TEMPORARY_DATA_FILE = "._data";
    
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
            Type[] types = fromStringsToTypes(columnTypes);
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
            Value[] values = fromStringsToValues(stringValues, table);
            
            String rowData = StringUtils.join(values, "|");
            
            fileSystemManager.appendToFile(new File(kuzoffHome, tableName), DATA_FILE, rowData);
            
            return new Row(values);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't insert row into table " + tableName, e);
        }
    }

    @Override
    public List<Row> getRows(String name) {
        try {
            File tableDirectory = new File(kuzoffHome, name);
            Table table = tableFromDirectory(tableDirectory);
            
            String tableData = fileSystemManager.readFromFile(tableDirectory, DATA_FILE);
            String[] tableRows = StringUtils.split(tableData, '\n');
            
            List<Row> rows = new ArrayList<>();
            for (String tableRow : tableRows) {
                String[] columns = StringUtils.split(tableRow, '|');
                Value[] values = fromStringsToValues(columns, table);
                
                rows.add(new Row(values));
            }
            
            return rows;
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't get rows from table " + name, e);
        }
    }
    
    @Override
    public List<Row> removeRow(String tableName, String[] values) {
        try {
            File tableDirectory = new File(kuzoffHome, tableName);
            Table table = tableFromDirectory(tableDirectory);
            
            Value[] valuesToDelete = fromStringsToValues(values, table);
            
            String tableData = fileSystemManager.readFromFile(tableDirectory, DATA_FILE);
            String[] tableRows = StringUtils.split(tableData, '\n');
            
            List<Row> rows = new ArrayList<>();
            List<Row> deletedRows = new ArrayList<>();
            for (String tableRow : tableRows) {
                String[] columns = StringUtils.split(tableRow, '|');
                Value[] rowValues = fromStringsToValues(columns, table);
                
                if (!Arrays.deepEquals(rowValues, valuesToDelete)) {
                    rows.add(new Row(rowValues));
                } else {
                    deletedRows.add(new Row(rowValues));
                }
            }
            
            fileSystemManager.rm(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE);
            fileSystemManager.touch(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE);
            for (Row row : rows) {
                String rowData = StringUtils.join(row.getValues(), "|");
                fileSystemManager.appendToFile(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE, rowData);
            }
            
            fileSystemManager.mv(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE, DATA_FILE);
            
            return deletedRows;
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't remove rows from table " + tableName, e);
        }
    }
    
    @Override
    public List<Row> removeDuplicates(String tableName) {
        try {
            File tableDirectory = new File(kuzoffHome, tableName);
            Table table = tableFromDirectory(tableDirectory);
            
            String tableData = fileSystemManager.readFromFile(tableDirectory, DATA_FILE);
            String[] tableRows = StringUtils.split(tableData, '\n');
            
            Set<Row> rows = new HashSet<>();
            for (String tableRow : tableRows) {
                String[] columns = StringUtils.split(tableRow, '|');
                Value[] rowValues = fromStringsToValues(columns, table);
                
                rows.add(new Row(rowValues));
            }
            
            fileSystemManager.rm(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE);
            fileSystemManager.touch(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE);
            for (Row row : rows) {
                String rowData = StringUtils.join(row.getValues(), "|");
                fileSystemManager.appendToFile(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE, rowData);
            }
            
            fileSystemManager.mv(new File(kuzoffHome, tableName), TEMPORARY_DATA_FILE, DATA_FILE);
            
            return new ArrayList<>(rows);
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't remove rows from table " + tableName, e);
        }
    }
    
    @Override
    public List<Row> difference(String tableName1, String tableName2) throws Exception {
        try {
            File tableDirectory1 = new File(kuzoffHome, tableName1);
            Table table1 = tableFromDirectory(tableDirectory1);
            
            String tableData1 = fileSystemManager.readFromFile(tableDirectory1, DATA_FILE);
            String[] tableRows1 = StringUtils.split(tableData1, '\n');
            
            List<Row> rows1 = new ArrayList<>();
            for (String tableRow : tableRows1) {
                String[] columns = StringUtils.split(tableRow, '|');
                Value[] rowValues = fromStringsToValues(columns, table1);
                
                rows1.add(new Row(rowValues));
            }
            
            File tableDirectory2 = new File(kuzoffHome, tableName2);
            Table table2 = tableFromDirectory(tableDirectory2);
            
            String tableData2 = fileSystemManager.readFromFile(tableDirectory2, DATA_FILE);
            String[] tableRows2 = StringUtils.split(tableData2, '\n');
            
            List<Row> rows2 = new ArrayList<>();
            for (String tableRow : tableRows2) {
                String[] columns = StringUtils.split(tableRow, '|');
                Value[] rowValues = fromStringsToValues(columns, table2);
                
                rows2.add(new Row(rowValues));
            }
            
            fileSystemManager.rm(new File(kuzoffHome, tableName1), TEMPORARY_DATA_FILE);
            fileSystemManager.touch(new File(kuzoffHome, tableName1), TEMPORARY_DATA_FILE);
            List<Row> result = new ArrayList<>();
            for (Row row : rows1) {
                if (!rows2.contains(row)) {
                    result.add(row);
                    String rowData = StringUtils.join(row.getValues(), "|");
                    fileSystemManager.appendToFile(new File(kuzoffHome, tableName1), TEMPORARY_DATA_FILE, rowData);
                }
            }
            
            fileSystemManager.mv(new File(kuzoffHome, tableName1), TEMPORARY_DATA_FILE, DATA_FILE);
            
            return result;
        } catch (Exception e) {
            throw new DatabaseManagerException("Can't find difference between " + tableName1 + " and "+ tableName2, e);
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
    
    private static Type[] fromStringsToTypes(String[] columnTypes) {
        Type[] types = new Type[columnTypes.length];
        for (int i = 0; i < columnTypes.length; i++) {
            types[i] = Type.getType(columnTypes[i]);
        }
        return types;
    }
    
    private static Value[] fromStringsToValues(String[] stringValues, Table table) {
        Type[] types = table.columnTypes();
        
        if (types.length != stringValues.length) {
            throw new IllegalArgumentException("Table " + table.getName() + " has " + types.length + " row(s), but " + stringValues.length + " value(s) was(were) passed");
        }
        
        Value[] values = new Value[stringValues.length];
        
        for (int i = 0; i < stringValues.length; i++) {
            values[i] = types[i].value(stringValues[i]);
        }
        return values;
    }
    
    public void setFileSystemManager(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }
}
