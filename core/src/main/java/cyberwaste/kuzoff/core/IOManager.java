package cyberwaste.kuzoff.core;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Value;

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

    public void outputTableInfo(Table table) {
        StringBuilder tableInfo = new StringBuilder();
        tableInfo.append(table.getName()).append(" (").append(StringUtils.join(table.getColumnTypes(), ", ")).append(")");
        outputResult(tableInfo.toString());
    }

    public void outputRowInfo(Row row) {
        StringBuilder rowInfo = new StringBuilder();
        
        for (Value value : row.getValues()) {
            if (rowInfo.length() > 0) {
                rowInfo.append("; ");
            }
            rowInfo.append(value.externalStringRepresentation());
        }
        rowInfo.insert(0, "{").append("}");
        
        outputResult(rowInfo.toString());
    }
    
    private void outputError(Exception e) {
        outputMessage(e.getClass().getName() + " error: " + e.getMessage());
    }
}
