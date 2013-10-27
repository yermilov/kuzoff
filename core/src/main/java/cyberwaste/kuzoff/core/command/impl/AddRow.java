package cyberwaste.kuzoff.core.command.impl;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Argument;
import cyberwaste.kuzoff.core.command.Command;

public class AddRow extends Command {
    
    @Argument(index = 0)
    private String tableName;

    @Override
    public void execute(IOManager ioManager) {
        // TODO Auto-generated method stub
        
    }
}
