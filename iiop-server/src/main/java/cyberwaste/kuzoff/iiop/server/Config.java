package cyberwaste.kuzoff.iiop.server;

import java.rmi.RemoteException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.FileSystemManager;
import cyberwaste.kuzoff.core.impl.DatabaseManagerImpl;
import cyberwaste.kuzoff.core.impl.FileSystemManagerImpl;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {
    
    @Bean
    public IIOPServerManager iiopServerManager() throws RemoteException {
        IIOPServerManager iiopServerManager = new IIOPServerManager();
        iiopServerManager.setRemoteDatabaseManager(serverIIOPDatabaseManager());
        
        return iiopServerManager;
    }
    
    @Bean
    public RemoteDatabaseManager serverIIOPDatabaseManager() throws RemoteException {
        RemoteDatabaseManagerImpl remoteDatabaseManager = new RemoteDatabaseManagerImpl();
        remoteDatabaseManager.setDelegate(databaseManager());
        
        return remoteDatabaseManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager databaseManager() {
        DatabaseManagerImpl databaseManager = new DatabaseManagerImpl();
        databaseManager.setFileSystemManager(fileSystemManager());
        return databaseManager;
    }

    @Bean
    public FileSystemManager fileSystemManager() {
        return new FileSystemManagerImpl();
    }
}
