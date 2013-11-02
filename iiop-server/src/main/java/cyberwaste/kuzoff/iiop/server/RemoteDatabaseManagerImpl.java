package cyberwaste.kuzoff.iiop.server;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.rmi.PortableRemoteObject;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public class RemoteDatabaseManagerImpl extends PortableRemoteObject implements RemoteDatabaseManager {
    
    private DatabaseManager delegate;

    protected RemoteDatabaseManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public Collection<Table> getAllTables() {
        return delegate.getAllTables();
    }

    @Override
    public Table createTable(String name, String[] columnTypes) {
        return delegate.createTable(name, columnTypes);
    }

    @Override
    public Table removeTable(String tableName) {
        return delegate.removeTable(tableName);
    }

    @Override
    public void removeDatabase() {
        delegate.removeDatabase();
    }

    @Override
    public Table getTable(String tableName) {
        return delegate.getTable(tableName);
    }

    @Override
    public Row insertRow(String tableName, String[] stringValues) {
        return delegate.insertRow(tableName, stringValues);
    }

    @Override
    public List<Row> getRows(String tableName) {
        return delegate.getRows(tableName);
    }

    @Override
    public List<Row> removeRow(String tableName, String[] values) {
        return delegate.removeRow(tableName, values);
    }

    @Override
    public List<Row> removeDuplicates(String tableName) {
        return removeDuplicates(tableName);
    }
    
    public void setDelegate(DatabaseManager delegate) {
        this.delegate = delegate;
    }
}
