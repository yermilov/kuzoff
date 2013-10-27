package cyberwaste.kuzoff.core;

public class DatabaseManagerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseManagerException(String message, Exception cause) {
        super(message, cause);
    }
}
