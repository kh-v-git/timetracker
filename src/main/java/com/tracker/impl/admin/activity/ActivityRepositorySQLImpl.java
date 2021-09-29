package com.tracker.impl.admin.activity;

import com.tracker.utils.DataBaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositorySQLImpl implements ActivityRepository {
    private static final Logger log = LogManager.getLogger(ActivityRepositorySQLImpl.class);

    private static final String SELECT_ALL_FROM_ACTIVITY = "SELECT * FROM activity";
    private static final String SELECT_LIKE_FROM_ACTIVITY_TEXT = "SELECT * FROM activity WHERE Lower(activityName) LIKE '%%%s%%'";
    private static final String SELECT_ALL_FROM_ACTIVITY_WITH_CATEGORY_ID = "SELECT * FROM activity WHERE categoryId='%d'";
    private static final String SELECT_LIKE_FROM_ACTIVITY_TEXT_WITH_CATEGORY_ID = "SELECT * FROM activity WHERE Lower(activityName) LIKE '%%%s%%' AND categoryId='%d'";
    private static final String INSERT_NEW_ACTIVITY = "INSERT INTO activity VALUES (DEFAULT, ?, ?, ?)";
    private static final String GET_ACTIVITY_BY_ID = "SELECT * FROM activity WHERE activityId='%d'";
    private static final String DELETE_ACTIVITY_BY_ID = "DELETE FROM activity WHERE activityId='%d'";
    private static final String UPDATE_ACTIVITY_BY_ID = "UPDATE activity SET categoryId='%d', activityName='%s', activityDescription='%s'  WHERE activityId='%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Activity activity;
    String sqlInsertion = null;

    @Override
    public List<Activity> findActivityList(String searchText, int catID) {
        con = null;
        pstmt = null;
        rs = null;
        List<Activity> activityList = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();

            sqlInsertion = querySelectorForList(searchText, catID);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                activity = new Activity();
                activity.setActivityId(rs.getInt("activityId"));
                activity.setActivityCatId(rs.getInt("categoryId"));
                activity.setActivityName(rs.getString("activityName"));
                activity.setActivityDescription(rs.getString("activityDescription"));
                activityList.add(activity);
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for activities List", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return activityList;
    }

    @Override
    public boolean setActivity(Activity activity) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(INSERT_NEW_ACTIVITY);
            pstmt.setInt(1, activity.getActivityCatId());
            pstmt.setString(2, activity.getActivityName());
            pstmt.setString(3, activity.getActivityDescription());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed add new activity to  DB", ex);
            rollback(con);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public Activity getActivity(int id) {
        con = null;
        pstmt = null;
        rs = null;
        activity = new Activity();
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            sqlInsertion = String.format(GET_ACTIVITY_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                activity.setActivityId(rs.getInt("activityId"));
                activity.setActivityCatId(rs.getInt("categoryId"));
                activity.setActivityName(rs.getString("activityName"));
                activity.setActivityDescription(rs.getString("activityDescription"));
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for activities by ID", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return activity;
    }

    @Override
    public boolean deleteActivity(int id) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(DELETE_ACTIVITY_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed delete activity by ID from DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public boolean updateActivity(Activity updActivity) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(UPDATE_ACTIVITY_BY_ID, updActivity.getActivityCatId(), updActivity.getActivityName(), updActivity.getActivityDescription(), updActivity.getActivityId());
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to update activity by ID in DB", ex);
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

    private static String querySelectorForList(String searchText, int categoryId) {
        if (searchText.isEmpty() && categoryId < 0) {
            return SELECT_ALL_FROM_ACTIVITY;
        }
        if (!searchText.isEmpty() && categoryId < 0) {
            return String.format(SELECT_LIKE_FROM_ACTIVITY_TEXT, searchText.toLowerCase());
        }
        if (searchText.isEmpty() && categoryId > 0) {
            return String.format(SELECT_ALL_FROM_ACTIVITY_WITH_CATEGORY_ID, categoryId);
        }
        return String.format(SELECT_LIKE_FROM_ACTIVITY_TEXT_WITH_CATEGORY_ID, searchText, categoryId);
    }
}
