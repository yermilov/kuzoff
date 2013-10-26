package cyberwaste.kuzoff.core.command;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.IOManager;

public interface Command {
    
    void setArguments(String[] arguments);
    
    void setDatabaseManager(DatabaseManager databaseManager);
    
    void execute(IOManager ioManager);
}
