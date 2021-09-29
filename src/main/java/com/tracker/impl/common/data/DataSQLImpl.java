package com.tracker.impl.common.data;


import com.tracker.utils.DataBaseManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public class DataSQLImpl implements DataRepository {
    private static final Logger log = LogManager.getLogger(DataSQLImpl.class);

    private static final String SELECT_ALL_USER_ACTIVITY_BY_ID =
            "SELECT user_activity.userActivityId, user_activity.activityId, user_activity.userId, " +
                    "user_activity.activityStatus, user_activity_time_log.timeLogId, user_activity_time_log.activityTimeLog, " +
                    "user_activity_time_log.activityStartDate, activity.activityName " +
                    "FROM (activity INNER JOIN user_activity ON user_activity.activityId=activity.activityId) " +
                    "LEFT OUTER JOIN user_activity_time_log ON user_activity.userActivityId=user_activity_time_log.userActivityId " +
                    "WHERE user_activity.userID = '%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    String sqlInsertion = null;

    @Override
    public List<AssignToUserActivity> findAllUserActivityList(int userId) {
        con = null;
        pstmt = null;
        rs = null;
        List<AssignToUserActivity> userActivityList = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();
            sqlInsertion = String.format(SELECT_ALL_USER_ACTIVITY_BY_ID, userId);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                AssignToUserActivity userActivity = new AssignToUserActivity();
                userActivity.setUserId(rs.getInt("userId"));
                userActivity.setActivityId(rs.getInt("activityId"));
                userActivity.setUserActivityId(rs.getInt("userActivityId"));
                userActivity.setTimeLogId(rs.getInt("timeLogId"));
                userActivity.setActivityStatus(rs.getString("activityStatus"));

                Integer getTimeLogged = rs.getInt("activityTimeLog");
                userActivity.setActivityTimeLog(getTimeLogged);
                userActivity.setActivityName(rs.getString("activityName"));

                LocalDate localDate = Optional.ofNullable(rs.getDate("activityStartDate"))
                        .map(Date::toLocalDate)
                        .orElse(null);
                userActivity.setActivityStartDate(localDate);
                userActivityList.add(userActivity);
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for AssignToUserActivity List", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return new ArrayList<>(userActivityList);
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
