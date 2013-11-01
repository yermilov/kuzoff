package cyberwaste.kuzoff.rmi.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import cyberwaste.kuzoff.core.DatabaseManager;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {

    @Bean
    public RmiClientManager rmiClientManager() {
        RmiClientManager rmiClientManager = new RmiClientManager();
        rmiClientManager.setDatabaseManager(remoteDatabaseManager());
        rmiClientManager.setReader(new BufferedReader(new InputStreamReader(System.in)));
        
        return rmiClientManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager remoteDatabaseManager() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost/DatabaseManager");
        rmiProxyFactoryBean.setServiceInterface(DatabaseManager.class);
        
        return (DatabaseManager) rmiProxyFactoryBean.getObject();
    }
}
