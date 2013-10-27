package cyberwaste.kuzoff.core.command.impl;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;

public class AddRow extends Command {
    
    @Argument(index = 0)
    private String tableName;
    
    @Argument(index = 1, eager = true)
    private String[] values;

    @Override
    public void execute(IOManager ioManager) {
        Row row = databaseManager.insertRow(tableName, values);
        ioManager.output("Insert 1 row:");
        ioManager.outputRowInfo(row);
    }
}
