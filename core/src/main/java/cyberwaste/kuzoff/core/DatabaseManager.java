package cyberwaste.kuzoff.core;

import java.util.Collection;

import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager {

    Collection<Table> getAllTables();

    Table createTable(String name, String[] columnTypes);

    Table removeTable(String name);

    void removeDatabase();

    Table getTable(String name);
}
