package cyberwaste.kuzoff.core;

import java.io.IOException;

import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Table;
public abstract class IOManager {
    
    protected abstract Command getNextCommand() throws IOException;

    protected abstract void outputResult(String message);
    
    protected abstract void outputMessage(String message);
    
    public void start() {
        boolean hasMoreCommands = true;
        
        while (hasMoreCommands) {
            try {
                Command command = getNextCommand();
                
                if (command != null) {
                    command.execute(this);
                } else {
                    hasMoreCommands = false;
                }
            } catch (Exception e) {
                outputError(e);
            }
        }
    }

    public void output(String message) {
        outputMessage(message);
    }

    public void outputShortTableInfo(Table table) {
        outputResult(table.getName());
    }
    
    private void outputError(Exception e) {
        outputMessage(e.getClass().getName() + " error: " + e.getMessage());
    }
}
