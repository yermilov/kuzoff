package cyberwaste.kuzoff.shell;

import java.io.BufferedReader;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cyberwaste.kuzoff.core.IOManager;
import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.command.CommandBuilder;

public class ShellManager extends IOManager {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Config.class);
    }
    
    private BufferedReader reader;
    private DatabaseManager databaseManager;
    
    @PostConstruct
    public void start() {
        super.start();
    }
    
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }
    
    @Override
    protected Command getNextCommand() throws IOException {
        String commandAsString = StringUtils.stripToEmpty(reader.readLine());
        
        if ("exit".equals(commandAsString)) {
            return null;
        }
        
        String[] commandTokens = commandAsString.split(" ");
        if (commandTokens.length < 2) {
            throw new IOException("Wrong command syntax");
        }
        
        String commandVerb = commandTokens[0];
        String commandNoun = commandTokens[1];
        String[] commandArguments = new String[commandTokens.length - 2];
        System.arraycopy(commandTokens, 2, commandArguments, 0, commandArguments.length);
        
        Command command = CommandBuilder
                             .command(commandVerb, commandNoun)
                             .usingDatabaseManager(databaseManager)
                             .withArguments(commandArguments)
                             .build();
        if (command == null) {
            throw new IOException("Wrong command syntax");
        }
        
        return command;
    }
    
    @Override
    protected void outputResult(String message) {
        System.out.println(message);
    }

    @Override
    protected void outputMessage(String message) {
        System.err.println(message);
    }
}
