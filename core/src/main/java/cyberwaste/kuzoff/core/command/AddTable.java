package cyberwaste.kuzoff.core.command;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.domain.Table;

public class AddTable extends Command {
    
    @Argument(index = 0)
    private String name;

    @Override
    public void execute(IOManager ioManager) {
        Table table = databaseManager.createTable(name);
        ioManager.output("Table created:");
        ioManager.outputShortTableInfo(table);
    }
}
