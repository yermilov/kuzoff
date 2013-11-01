package cyberwaste.kuzoff.rmi.server;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.RmiServiceExporter;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.FileSystemManager;
import cyberwaste.kuzoff.core.impl.DatabaseManagerImpl;
import cyberwaste.kuzoff.core.impl.FileSystemManagerImpl;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {
    
    @Bean
    public RmiServiceExporter serverDatabaseManager() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(databaseManager());
        rmiServiceExporter.setServiceName("DatabaseManager");
        rmiServiceExporter.setServiceInterface(DatabaseManager.class);
        
        return rmiServiceExporter;
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
