package com.tracker.impl.user.useractivitylog;

import com.tracker.utils.DataBaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserActivityTimeLogSQLImpl implements UserActivityTimeLogRepository {
    private static final Logger log = LogManager.getLogger(UserActivityTimeLogSQLImpl.class);

    private static final String SELECT_ALL_ACTIVITY_TIME_LOG = "SELECT * FROM user_activity_time_log";
    private static final String INSERT_NEW_USER_ACTIVITY_TIME_LOG = "INSERT INTO user_activity_time_log VALUES (DEFAULT, ?, ?, ?)";
    private static final String GET_USER_ACTIVITY_LOG_BY_ID = "SELECT * FROM user_activity_time_log WHERE timeLogId='%d'";
    private static final String DELETE_USER_ACTIVITY_LOG_BY_ID = "DELETE FROM user_activity_time_log WHERE timeLogId='%d'";
    private static final String UPDATE_USER_ACTIVITY_LOG_BY_ID = "UPDATE user_activity_time_log SET userActivityId='%d', activityStartDate='%t', activityTimeLog='%d'  WHERE timeLogId='%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private UserActivityTimeLog userActivityTimeLog;
    String sqlInsertion = null;


    @Override
    public List<UserActivityTimeLog> findUserActivityTimeLogList() {
        con = null;
        pstmt = null;
        rs = null;
        List<UserActivityTimeLog> userActivityTimeLogs = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();

            sqlInsertion = SELECT_ALL_ACTIVITY_TIME_LOG;
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userActivityTimeLog = new UserActivityTimeLog();
                userActivityTimeLog.setTimeLogId(rs.getInt("timeLogId"));
                userActivityTimeLog.setUserActivityId(rs.getInt("userActivityId"));
                LocalDate localDate = Optional.ofNullable(rs.getDate("activityStartDate"))
                        .map(Date::toLocalDate)
                        .orElse(null);
                userActivityTimeLog.setActivityStartDate(localDate);
                userActivityTimeLog.setActivityTimeLog(rs.getInt("activityTimeLog"));
                userActivityTimeLogs.add(userActivityTimeLog);
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for userActivityTimeLogId list", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return userActivityTimeLogs;
    }

    @Override
    public boolean setUserActivityTimeLog(UserActivityTimeLog userActivityTimeLog) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(INSERT_NEW_USER_ACTIVITY_TIME_LOG);
            pstmt.setInt(1, userActivityTimeLog.getUserActivityId());
            pstmt.setDate(2, java.sql.Date.valueOf(userActivityTimeLog.getActivityStartDate()));
            pstmt.setInt(3, userActivityTimeLog.getActivityTimeLog());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed add new userActivityTimeLog to  DB", ex);
            rollback(con);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public UserActivityTimeLog getUserActivityTimeLog(int userActivityTimeLogId) {
        con = null;
        pstmt = null;
        rs = null;
        userActivityTimeLog = new UserActivityTimeLog();
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            sqlInsertion = String.format(GET_USER_ACTIVITY_LOG_BY_ID, userActivityTimeLogId);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userActivityTimeLog.setTimeLogId(rs.getInt("timeLogId"));
                userActivityTimeLog.setUserActivityId(rs.getInt("userActivityId"));
                LocalDate localDate = Optional.ofNullable(rs.getDate("activityStartDate"))
                        .map(Date::toLocalDate)
                        .orElse(null);
                userActivityTimeLog.setActivityStartDate(localDate);
                userActivityTimeLog.setActivityTimeLog(rs.getInt("activityTimeLog"));
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to get userActivityTimeLogId by ID", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return userActivityTimeLog;
    }

    @Override
    public boolean updateUserActivityTimeLog(UserActivityTimeLog userActivityTimeLog) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(UPDATE_USER_ACTIVITY_LOG_BY_ID, userActivityTimeLog.getUserActivityId(), java.sql.Date.valueOf(userActivityTimeLog.getActivityStartDate()), userActivityTimeLog.getTimeLogId(), userActivityTimeLog.getTimeLogId());
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to updateUserActivityTimeLog by ID in DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public boolean deleteUserActivityTimeLog(int userActivityTimeLogId) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(DELETE_USER_ACTIVITY_LOG_BY_ID, userActivityTimeLogId);
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed delete userActivityTimeLogId by ID from DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.log(Level.ERROR, "DB close failed", e);
            }
        }
    }

    private static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException e) {
                log.log(Level.ERROR, "DB rollback failed", e);
            }
        }
    }
}
