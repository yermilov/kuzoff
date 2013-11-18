package cyberwaste.kuzoff.iiop.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import cyberwaste.kuzoff.core.DatabaseManager;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {

    @Bean
    public IIOPClientManager iiopClientManager() throws NamingException {
        IIOPClientManager iiopClientManager = new IIOPClientManager();
        iiopClientManager.setDatabaseManager(remoteDatabaseManager());
        iiopClientManager.setReader(new BufferedReader(new InputStreamReader(System.in)));
        
        return iiopClientManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager remoteDatabaseManager() throws NamingException {
        Context ic = new InitialContext();
        
        Object objref = ic.lookup("DatabaseService");
        Assert.notNull(objref);
        
        DatabaseManager databaseManager = (DatabaseManager) PortableRemoteObject.narrow(objref, DatabaseManager.class);
        Assert.notNull(databaseManager);
        
        return databaseManager;
    }
}
