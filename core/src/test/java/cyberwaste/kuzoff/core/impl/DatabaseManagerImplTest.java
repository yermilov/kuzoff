package cyberwaste.kuzoff.core.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import cyberwaste.kuzoff.core.FileSystemManager;
import cyberwaste.kuzoff.core.domain.Table;

public class DatabaseManagerImplTest {

    @Test
    public void test_getAllTables() throws IOException {
        // setup
        File table1 = new File("table1");
        File table2 = new File("table2");
        List<File> filesList = Arrays.asList(table1, table2);
        
        FileSystemManager fileSystemManager = Mockito.mock(FileSystemManager.class);
        Mockito.when(fileSystemManager.getSubdirectories((File) any())).thenReturn(filesList);
        Mockito.when(fileSystemManager.readFromFile(eq(table1), eq(DatabaseManagerImpl.METADATA_FILE))).thenReturn("");
        Mockito.when(fileSystemManager.readFromFile(eq(table2), eq(DatabaseManagerImpl.METADATA_FILE))).thenReturn("");
        
        DatabaseManagerImpl databaseManagerImpl = new DatabaseManagerImpl();
        databaseManagerImpl.setFileSystemManager(fileSystemManager);
        
        // run
        Collection<Table> actual = databaseManagerImpl.getAllTables();
        
        // verify
        Collection<Table> expected = Arrays.asList(new Table("table1"), new Table("table2"));
        assertEquals(expected, actual);
    }
}
