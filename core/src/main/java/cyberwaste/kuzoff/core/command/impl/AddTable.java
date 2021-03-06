package cyberwaste.kuzoff.core.command.impl;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Table;

public class AddTable extends Command {
    
    @Argument(index = 0)
    private String name;
    
    @Argument(index = 1, eager = true)
    private String[] columnTypes;
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        Table table = databaseManager.createTable(name, columnTypes);
        ioManager.output("Table created:");
        ioManager.outputShortTableInfo(table);
    }
}
