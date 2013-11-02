package cyberwaste.kuzoff.core.command.impl;

import java.util.List;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;

public class UniqueTable extends Command {
    
    @Argument(index = 0)
    private String name;
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        List<Row> rows = databaseManager.removeDuplicates(name);
        
        ioManager.output("Leave " + rows.size() + " row(s):");
        for (Row row : rows) {
            ioManager.outputRowInfo(row);
        }
    }
}
