package cyberwaste.kuzoff.ws.server;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

@Controller
@RequestMapping("/api")
public class WebServiceDatabaseManagerFacade implements DatabaseManager {
    
    @Autowired
    private DatabaseManager delegate;

    @Override
    @RequestMapping(value="/table", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Collection<Table> getAllTables() throws Exception {
        return delegate.getAllTables();
    }

    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Table createTable(@PathVariable("tableName") String name, @RequestParam("columnTypes") String[] columnTypes) throws Exception {
        return delegate.createTable(name, columnTypes);
    }

    @Override
    @RequestMapping(value="/database", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void removeDatabase() throws Exception {
        delegate.removeDatabase();
    }

    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Table getTable(@PathVariable("tableName") String tableName) throws Exception {
        return delegate.getTable(tableName);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Row insertRow(@PathVariable("tableName") String tableName, @RequestParam("data") String[] stringValues) throws Exception {
        return delegate.insertRow(tableName, stringValues);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Row> getRows(@PathVariable("tableName") String tableName) throws Exception {
        return delegate.getRows(tableName);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.DELETE, produces="application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody List<Row> removeRow(@PathVariable("tableName") String tableName, @RequestParam("data") String[] values) throws Exception {
        return delegate.removeRow(tableName, values);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/unique", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody List<Row> removeDuplicates(@PathVariable("tableName") String tableName) throws Exception {
        return delegate.removeDuplicates(tableName);
    }
    
    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.DELETE, produces="application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody Table removeTable(@PathVariable("tableName") String tableName) throws Exception {
        return delegate.removeTable(tableName);
    }
    
    @Override
    @RequestMapping(value="/table/{tableName1}/diff/{tableName2}", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody List<Row> difference(@PathVariable("tableName1") String tableName1, @PathVariable("tableName2") String tableName2) throws Exception {
        return delegate.difference(tableName1, tableName2);
    }
    
    public void setDelegate(DatabaseManager delegate) {
        this.delegate = delegate;
    }
}
