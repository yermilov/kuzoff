package cyberwaste.kuzoff.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Table;

public class DatabaseManagerImpl implements DatabaseManager {
    
    private static final File KUZOFF_HOME = new File(FileUtils.getUserDirectory(), "kuzoff");
    static {
        if (!KUZOFF_HOME.exists()) {
            KUZOFF_HOME.mkdirs();
        }
    }

    @Override
    public Collection<Table> getAllTables() {
        Collection<File> tableDirectories = FileUtils.listFilesAndDirs(
            KUZOFF_HOME, 
            new IOFileFilter() {
                
                @Override
                public boolean accept(File dir, String name) {
                    return false;
                }
                
                @Override
                public boolean accept(File file) {
                    return false;
                }
            }, 
            new IOFileFilter() {
                
                @Override
                public boolean accept(File dir, String name) {
                    return KUZOFF_HOME.equals(dir);
                }
                
                @Override
                public boolean accept(File file) {
                    return KUZOFF_HOME.equals(file.getParentFile());
                }
            }
        );
        
        Collection<Table> tables = new ArrayList<>();
        for (File tableDirectory : tableDirectories) {
            tables.add(tableFromDirectory(tableDirectory));
        }
        return tables;
    }

    private Table tableFromDirectory(File tableDirectory) {
        return new Table(tableDirectory.getName());
    }
}
