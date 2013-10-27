package cyberwaste.kuzoff.core.command;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cyberwaste.kuzoff.core.DatabaseManager;

public class CommandBuilder {
    
    private DatabaseManager databaseManager;
    private String verb;
    private String noun;
    private String[] arguments;

    public static CommandBuilder command(String verb, String noun) {
        return new CommandBuilder(verb, noun);
    }

    private CommandBuilder(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }
    
    public CommandBuilder usingDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        return this;
    }

    public CommandBuilder withArguments(String[] arguments) {
        this.arguments = (String[]) ArrayUtils.clone(arguments);
        return this;
    }

    public Command build() {
        try {
            String commandClassName = "cyberwaste.kuzoff.core.command.impl." + StringUtils.capitalize(verb) + StringUtils.capitalize(noun);
            Class<?> commandClass = Class.forName(commandClassName);
            if (commandClass == null) {
                return null;
            }
            Command commandInstance = (Command) commandClass.newInstance();
            commandInstance.setDatabaseManager(databaseManager);
            for (Field field : commandClass.getDeclaredFields()) {
                Argument annotation = field.getAnnotation(Argument.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    
                    if (annotation.eager()) {
                        String[] argumentSuffix = (String[]) ArrayUtils.subarray(arguments, annotation.index(), arguments.length); 
                        field.set(commandInstance, argumentSuffix);
                    } else {
                        int index = annotation.index();
                        
                        field.set(commandInstance, arguments[index]);
                    }
                    
                    field.setAccessible(false);
                }
            }
            
            return commandInstance;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        } 
    }
}
