package g52977.stib.jdbc;

import g52977.stib.config.ConfigManager;
import g52977.stib.exception.RepositoryException;
import java.sql.*;

/**
 * Manages the connection and the access to the database.
 * 
 * @author Maximilien Faucon 52977
 */
class DBManager {

    private Connection connection;

    private DBManager() {
    }

    /**
     * Makes the connection to the data base. Throw an error if the connection
     * failed.
     * 
     * @return The connection to the data base.
     * @throws RepositoryException Exception thrown if the connection failed.
     */
    Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        //|| connection.isClosed()
        if (connection == null ) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * Starts a transaction with the database.
     * 
     * @throws RepositoryException Error thrown if the transaction failed.
     */
    void startTransaction() throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * Starts the transaction with the data base.
     * 
     * @param isolationLevel The isolation level.
     * @throws RepositoryException Exception thrown if the transaction didn't
     * work.
     */
    void startTransaction(int isolationLevel) throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0:
                    isol = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                    break;
                case 1:
                    isol = java.sql.Connection.TRANSACTION_READ_COMMITTED;
                    break;
                case 2:
                    isol = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                    break;
                case 3:
                    isol = java.sql.Connection.TRANSACTION_SERIALIZABLE;
                    break;
                default:
                    throw new RepositoryException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de démarrer une transaction: " + ex.getMessage());
        }
    }

    /**
     * Validates a transaction.
     * 
     * @throws RepositoryException Exception thrown if the validation of the 
     * transaction failed. 
     */
    void validateTransaction() throws RepositoryException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible de valider la transaction: " + ex.getMessage());
        }
    }

    /**
     * Cancel a transaction.
     * 
     * @throws RepositoryException Exception thrown if the cancel of the transaction
     * failed.
     */
    void cancelTransaction() throws RepositoryException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible d'annuler la transaction: " + ex.getMessage());
        }
    }

    static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}
