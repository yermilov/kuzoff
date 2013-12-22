package cyberwaste.kuzoff.core.command.impl;

import java.util.List;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;

public class DiffTables extends Command {
    
    @Argument(index = 0)
    private String name1;
    
    @Argument(index = 1)
    private String name2;
    
    @Override
    public void execute(IOManager ioManager) throws Exception {
        List<Row> rows = databaseManager.difference(name1, name2);
        
        ioManager.output("Leave " + rows.size() + " row(s):");
        for (Row row : rows) {
            ioManager.outputRowInfo(row);
        }
    }
}
