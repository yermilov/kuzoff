package cyberwaste.kuzoff.ws.groovyclient

import groovyx.net.http.RESTClient

class WebServiceGroovyClient {
    
    static def client = new RESTClient('http://localhost:8080/kuzoff-ws/api/')
    
    static main(args) {
        while (true) {
            def command = System.console().readLine ">>>"
            
            if (command == 'exit') return
            def commandTokens = command.split(" ")
            
            if (commandTokens[0] == 'show' && commandTokens[1] == 'database') {
                showDatabase();
            }
            if (commandTokens[0] == 'show' && commandTokens[1] == 'table') {
                showTable(commandTokens[2]);
            }
            if (commandTokens[0] == 'select' && commandTokens[1] == 'table') {
                selectTable(commandTokens[2]);
            }
            if (commandTokens[0] == 'unique' && commandTokens[1] == 'table') {
                uniqueTable(commandTokens[2]);
            }
        }
    }
    
    static showDatabase() {
        try {
            def result = client.get(path : "table")
            
            println "Found ${result.data.size()} tables"
            for (def table : result.data) {
                println "${table.name}"
            }
        } catch (e) {
            println e.message
        }
    }
    
    static showTable(def tableName) {
        try {
            def result = client.get(path : "table/${tableName}")
            
            print "table ${result.data.name} ("
            for (def column : result.data.typeNames) {
                print "${column}"
            }
            println ")"
        } catch (e) {
            println e.message
        }
    }
    
    static selectTable(def tableName) {
        try {
            def result = client.get(path : "table/${tableName}/data")
            
            println "Found ${result.data.values.size()} rows"
            for (def row : result.data) {
                for (def value : row.values) {
                    print "${value.valueAsString} "
                }
                
                println()
            }
        } catch (e) {
            println e.message
        }
    }
    
    static uniqueTable(def tableName) {
        try {
            def result = client.post(path : "table/${tableName}/unique")
            
            println "Found ${result.data.values.size()} rows"
            for (def row : result.data) {
                for (def value : row.values) {
                    print "${value.valueAsString} "
                }
                
                println()
            }
        } catch (e) {
            println e.message
        }
    }
}
