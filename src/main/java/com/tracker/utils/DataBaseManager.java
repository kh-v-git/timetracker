package com.tracker.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DataBaseManager {
    private static final Logger log = LogManager.getLogger(DataBaseManager.class);

    private static DataBaseManager dbPool;
    private DataSource dsPool;

    private DataBaseManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dsPool = (DataSource) envContext.lookup("jdbc/DataBaseTimeTracker");
        } catch (NamingException ex) {
            log.log(Level.ERROR, "Cannot initialize DB connection", ex);
        }
    }

    public static synchronized DataBaseManager getInstance() {
        if (dbPool == null) {
            dbPool = new DataBaseManager();
        }
        return dbPool;
    }

    public Connection getConnection() throws SQLException {
        return dsPool.getConnection();
    }
}
