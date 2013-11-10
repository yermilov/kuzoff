package cyberwaste.kuzoff.corba.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cyberwaste.kuzoff.shell.ShellManager;

public class CorbaClientManager extends ShellManager {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Config.class);
    }
}
