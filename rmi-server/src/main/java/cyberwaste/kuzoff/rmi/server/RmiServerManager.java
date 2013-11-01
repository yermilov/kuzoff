package cyberwaste.kuzoff.rmi.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RmiServerManager {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Config.class);
    }
}
