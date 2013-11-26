package cyberwaste.kuzoff.ws.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cyberwaste.kuzoff.shell.ShellManager;

public class WebServiceClientManager extends ShellManager {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Config.class);
    }
}
