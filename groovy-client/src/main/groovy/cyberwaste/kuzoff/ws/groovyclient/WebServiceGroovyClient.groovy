package cyberwaste.kuzoff.ws.groovyclient

class WebServiceGroovyClient {
    
    static main(args) {
        def client = new WebServiceGroovyClient();
        
        Binding binding = new Binding()
        GroovyShell shell = new GroovyShell(binding)
        
        while (true) {
            def command = System.console().readLine ">>>"
            if (command == 'exit') return
            println "${command}"
        }
    }
}
