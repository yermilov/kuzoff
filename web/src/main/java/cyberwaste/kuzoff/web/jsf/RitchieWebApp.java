package cyberwaste.kuzoff.web.jsf;

import java.io.IOException;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.command.CommandBuilder;

public class RitchieWebApp {

    private DatabaseManager databaseManager;
    
    public String handleCommand(String commandVerb, String[] params) throws Exception {
        String commandNoun = params[0];
        String[] commandArguments = new String[params.length - 1];
        System.arraycopy(params, 1, commandArguments, 0, commandArguments.length);
        
        Command command = CommandBuilder
                             .command(commandVerb, commandNoun)
                             .usingDatabaseManager(databaseManager)
                             .withArguments(commandArguments)
                             .build();
        if (command == null) {
            return "Wrong command syntax";
        }
        
        CollectResult collectResult = new CollectResult();
        command.execute(collectResult);
        
        return collectResult.result;
    }
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    private class CollectResult extends IOManager {
        
        private String result = "";

        @Override
        protected Command getNextCommand() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void outputResult(String message) {
            result += message + "\n";
        }

        @Override
        protected void outputMessage(String message) {
            result += message + "\n";
        }
    }
}
