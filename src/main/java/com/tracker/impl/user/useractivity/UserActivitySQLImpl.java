package com.tracker.impl.user.useractivity;

import com.tracker.utils.DataBaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserActivitySQLImpl implements UserActivityRepository {
    private static final Logger log = LogManager.getLogger(UserActivitySQLImpl.class);

    private static final String SELECT_ALL_USER_ACTIVITY = "SELECT * FROM user_activity ";
    private static final String INSERT_NEW_USER_ACTIVITY = "INSERT INTO user_activity VALUES (DEFAULT, ?, ?, ?)";
    public static final String GET_USER_ACTIVITY_BY_ID = "SELECT * FROM user_activity WHERE userActivityId='%d'";
    private static final String DELETE_USER_ACTIVITY_BY_ID = "DELETE FROM user_activity WHERE userActivityId='%d'";
    private static final String UPDATE_USER_ACTIVITY_BY_ID = "UPDATE user_activity SET userId='%d', activityId='%d', activityStatus='%s'  WHERE userActivityId='%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    String sqlInsertion = null;
    UserActivity userActivity;

    @Override
    public List<UserActivity> findUserActivityList() {
        con = null;
        pstmt = null;
        rs = null;
        List<UserActivity> userActivitiesList = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();
            sqlInsertion = String.format(SELECT_ALL_USER_ACTIVITY);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userActivity = new UserActivity();
                userActivity.setActivityId(rs.getInt("activityId"));
                userActivity.setUserActivityId(rs.getInt("userActivityId"));
                userActivity.setUserId(rs.getInt("userId"));
                userActivity.setActivityStatus(rs.getString("activityStatus"));
                userActivitiesList.add(userActivity);
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for userActivities List", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return new ArrayList<>(userActivitiesList);
    }

    @Override
    public boolean setUserActivity(UserActivity inpUserAct) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(INSERT_NEW_USER_ACTIVITY);
            pstmt.setInt(1, inpUserAct.getUserId());
            pstmt.setInt(2, inpUserAct.getActivityId());
            pstmt.setString(3, inpUserAct.getActivityStatus());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed add new userActivity to  DB", ex);
            rollback(con);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public UserActivity getUserActivity(int userActivityId) {
        con = null;
        pstmt = null;
        rs = null;
        userActivity = new UserActivity();
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            sqlInsertion = String.format(GET_USER_ACTIVITY_BY_ID, userActivityId);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userActivity.setActivityId(rs.getInt("activityId"));
                userActivity.setUserActivityId(rs.getInt("userActivityId"));
                userActivity.setUserId(rs.getInt("userId"));
                userActivity.setActivityStatus(rs.getString("activityStatus"));
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for getUserActivity by ID", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return userActivity;
    }

    @Override
    public boolean updateUserActivity(UserActivity userActivity) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(UPDATE_USER_ACTIVITY_BY_ID, userActivity.getUserId(), userActivity.getActivityId(), userActivity.getActivityStatus(), userActivity.getUserActivityId());
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to updateUserActivity by ID in DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public boolean deleteUserActivity(int userActivityId) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(DELETE_USER_ACTIVITY_BY_ID, userActivityId);
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed delete userActivityId by ID from DB", ex);
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
