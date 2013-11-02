package cyberwaste.kuzoff.core.command.impl;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Table;

public class DropTable extends Command {
    
    @Argument(index = 0)
    private String name;
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        Table table = databaseManager.removeTable(name);
        ioManager.output("Table droped:");
        ioManager.outputShortTableInfo(table);
    }
}
