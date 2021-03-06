package cyberwaste.kuzoff.core.command.impl;

import java.util.Collection;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Table;

public class ShowDatabase extends Command {
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        Collection<Table> allTables = databaseManager.getAllTables();
        ioManager.output("Total tables count: " + allTables.size());
        for (Table table : allTables) {
            ioManager.outputShortTableInfo(table);
        }
    }
}
