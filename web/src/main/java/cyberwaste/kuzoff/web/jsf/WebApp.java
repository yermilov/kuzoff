package cyberwaste.kuzoff.web.jsf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public class WebApp {

    private DatabaseManager databaseManager;
    
    private String primaryTable;
    private String secondaryTable;
    private List<String> newRowValues;
    
    public String getPrimaryTable() {
        return primaryTable;
    }
    
    public void setPrimaryTable(String primaryTable) throws IOException {
        this.primaryTable = primaryTable;
        clearNewRowValue();
    }
    
    public String getSecondaryTable() {
        return secondaryTable;
    }
    
    public void setSecondaryTable(String secondaryTable) {
        this.secondaryTable = secondaryTable;
    }
    
    public Collection<String> getAllTables() throws IOException {
        try {
            Collection<String> tables = new ArrayList<String>();
            for (Table table : databaseManager.getAllTables()) {
                tables.add(table.getName());
            }
            return tables;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<Row> getTableData() throws IOException {
        try {
            return databaseManager.getRows(primaryTable);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<String> getTableColumnTypes() throws IOException {
        try {
            return Arrays.asList(databaseManager.getTable(primaryTable).getTypeNames());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<List<String>> getNewRowValues() {
        return Collections.singletonList(newRowValues);
    }
    
    public void setNewRow(List<List<String>> newRow) {
        this.newRowValues = newRow.get(0);
    }
  
    public void addRow() {
        try {
            databaseManager.insertRow(primaryTable, newRowValues.toArray(new String[0]));
            clearNewRowValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void unique() {
        try {
            databaseManager.removeDuplicates(primaryTable);
            clearNewRowValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> clearNewRowValue() throws IOException {
        newRowValues  = new ArrayList<String>();
        for (int i = 0; i < getTableColumnTypes().size(); i++) {
            newRowValues.add("");
        }
        return newRowValues;
    }
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
