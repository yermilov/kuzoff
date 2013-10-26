package cyberwaste.kuzoff.core.command;

import java.util.Collection;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.domain.Table;

public class ShowDatabase implements Command {
    
    private DatabaseManager databaseManager;

    @Override
    public void setArguments(String[] arguments) { }

    @Override
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void execute(IOManager ioManager) {
        Collection<Table> allTables = databaseManager.getAllTables();
        ioManager.output("Total tables count: " + allTables.size());
        for (Table table : allTables) {
            ioManager.outputShortTableInfo(table);
        }
    }
}
