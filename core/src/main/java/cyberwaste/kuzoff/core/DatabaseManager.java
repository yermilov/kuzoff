package cyberwaste.kuzoff.core;

import java.util.Collection;
import java.util.List;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager {

    Collection<Table> getAllTables();

    Table createTable(String name, String[] columnTypes);

    Table removeTable(String tableName);

    void removeDatabase();

    Table getTable(String tableName);

    Row insertRow(String tableName, String[] stringValues);

    List<Row> getRows(String tableName);

    List<Row> removeRow(String tableName, String[] values);

    List<Row> removeDuplicates(String tableName);
}
