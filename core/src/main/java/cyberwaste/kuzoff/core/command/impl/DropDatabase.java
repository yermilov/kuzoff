package cyberwaste.kuzoff.core.command.impl;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Command;

public class DropDatabase extends Command {

    @Override
    public void execute(IOManager ioManager) {
        databaseManager.removeDatabase();
        ioManager.output("Database droped");
    }
}
