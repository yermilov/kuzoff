package cyberwaste.kuzoff.core.command;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.IOManager;

public abstract class Command {
    
    protected DatabaseManager databaseManager;
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    public abstract void execute(IOManager ioManager);
}
