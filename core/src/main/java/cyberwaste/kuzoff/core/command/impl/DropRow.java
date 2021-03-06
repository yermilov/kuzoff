package cyberwaste.kuzoff.core.command.impl;

import java.util.List;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;

public class DropRow extends Command {
    
    @Argument(index = 0)
    private String tableName;
    
    @Argument(index = 1, eager = true)
    private String[] values;

    @Override
    public void execute(IOManager ioManager) throws Exception {
        List<Row> removedRows = databaseManager.removeRow(tableName, values);
        
        ioManager.output("Droped " + removedRows.size() + " row(s):");
        for (Row row : removedRows) {
            ioManager.outputRowInfo(row);
        }
    }
}
