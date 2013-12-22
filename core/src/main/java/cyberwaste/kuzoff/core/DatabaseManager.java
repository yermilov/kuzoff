package cyberwaste.kuzoff.core;

import java.util.Collection;
import java.util.List;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager {

    Collection<Table> getAllTables() throws Exception;

    Table createTable(String name, String[] columnTypes) throws Exception;

    Table removeTable(String tableName) throws Exception;

    void removeDatabase() throws Exception;

    Table getTable(String tableName) throws Exception;

    Row insertRow(String tableName, String[] stringValues) throws Exception;

    List<Row> getRows(String tableName) throws Exception;

    List<Row> removeRow(String tableName, String[] values) throws Exception;

    List<Row> removeDuplicates(String tableName) throws Exception;
    
    List<Row> difference(String tableName1, String tableName2) throws Exception;
}
