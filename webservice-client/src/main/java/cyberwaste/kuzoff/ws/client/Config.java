package cyberwaste.kuzoff.ws.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import cyberwaste.kuzoff.core.DatabaseManager;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {

    @Bean
    public WebServiceClientManager webServiceClientManager() {
        WebServiceClientManager webServiceClientManager = new WebServiceClientManager();
        webServiceClientManager.setDatabaseManager(webServiceDatabaseManager());
        webServiceClientManager.setReader(new BufferedReader(new InputStreamReader(System.in)));
        
        return webServiceClientManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager webServiceDatabaseManager() {
        WebServiceDatabaseManager webServiceDatabaseManager = new WebServiceDatabaseManager();
        webServiceDatabaseManager.setRestTemplate(restTemplate());
        return webServiceDatabaseManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
