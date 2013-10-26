package cyberwaste.kuzoff.core;

import java.util.Collection;

import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager {

    Collection<Table> getAllTables();
}
