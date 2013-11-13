package cyberwaste.kuzoff.http.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import cyberwaste.kuzoff.core.DatabaseManager;

@Configuration
@ComponentScan("cyberwaste.kuzoff")
class Config {

    @Bean
    public HttpClientManager httpClientManager() {
        HttpClientManager httpClientManager = new HttpClientManager();
        httpClientManager.setDatabaseManager(remoteDatabaseManager());
        httpClientManager.setReader(new BufferedReader(new InputStreamReader(System.in)));
        
        return httpClientManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DatabaseManager remoteDatabaseManager() {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:8080/kuzoff-http/kuzoff.service");
        httpInvokerProxyFactoryBean.setServiceInterface(DatabaseManager.class);
        httpInvokerProxyFactoryBean.afterPropertiesSet();
        
        return (DatabaseManager) httpInvokerProxyFactoryBean.getObject();
    }
}
