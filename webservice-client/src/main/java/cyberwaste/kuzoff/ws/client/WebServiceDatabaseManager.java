package cyberwaste.kuzoff.ws.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public class WebServiceDatabaseManager implements DatabaseManager {
    
    private final static String SERVICE_URL = "http://localhost:8080/kuzoff-ws/api";
    
    private RestTemplate restTemplate;

    @Override
    public Collection<Table> getAllTables() throws Exception {
        Table[] result = restTemplate.getForObject(SERVICE_URL + "/table", Table[].class, Collections.emptyMap());
        return Arrays.asList(result);
    }

    @Override
    public Table createTable(String name, String[] columnTypes) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("columnTypes", StringUtils.join(columnTypes, ','));
        
        return restTemplate.postForObject(SERVICE_URL + "/table/{name}?columnTypes={columnTypes}", null, Table.class, parameters);
    }

    @Override
    public Table removeTable(String tableName) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        
        restTemplate.delete(SERVICE_URL + "/table/{tableName}", parameters);
        return new Table(tableName);
    }

    @Override
    public void removeDatabase() throws Exception {
        restTemplate.delete(SERVICE_URL + "/database");
    }

    @Override
    public Table getTable(String tableName) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        
        return restTemplate.getForObject(SERVICE_URL + "/table/{tableName}", Table.class, parameters);
    }

    @Override
    public Row insertRow(String tableName, String[] stringValues) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        parameters.put("data", StringUtils.join(stringValues, ','));
        
        return restTemplate.postForObject(
            SERVICE_URL + "/table/{tableName}/data/?data={data}", null, Row.class, parameters
        );
    }

    @Override
    public List<Row> getRows(String tableName) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        
        Row[] result = restTemplate.getForObject(SERVICE_URL + "/table/{tableName}/data", Row[].class, parameters);
        return Arrays.asList(result);
    }

    @Override
    public List<Row> removeRow(String tableName, String[] values) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        parameters.put("data", StringUtils.join(values, ','));
        
        restTemplate.delete(SERVICE_URL + "/table/{tableName}/data/?data={data}", parameters);
        return Collections.emptyList();
    }

    @Override
    public List<Row> removeDuplicates(String tableName) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tableName", tableName);
        
        Row[] result = restTemplate.postForObject(SERVICE_URL + "/table/{tableName}/unique", null, Row[].class, parameters);
        return Arrays.asList(result);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
