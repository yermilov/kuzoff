package cyberwaste.kuzoff.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.impl.DatabaseManagerImpl;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {

    @Bean
    public ShellManager shellManager() {
        ShellManager shellManager = new ShellManager();
        shellManager.setDatabaseManager(databaseManager());
        shellManager.setReader(new BufferedReader(new InputStreamReader(System.in)));
        
        return shellManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager databaseManager() {
        return new DatabaseManagerImpl();
    }
}
