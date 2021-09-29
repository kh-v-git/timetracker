package com.tracker.impl.user.user;

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
import java.util.Optional;

public class UserRepositorySQLImpl implements UserRepository {
    private static final Logger log = LogManager.getLogger(UserRepositorySQLImpl.class);

    private static final String SELECT_ALL_FROM_USER = "SELECT * FROM user";
    private static final String SELECT_LIKE_FROM_USER = "SELECT * FROM user WHERE Lower(userEmail) LIKE '%%%s%%'";
    public static final String FIND_ALL_FROM_USER_EMAIL = "SELECT user.userId, user.userEmail, user.userPassword, user.userRole, user.userStatus  FROM user WHERE userEmail='%s'";
    private static final String INSERT_NEW_USER = "INSERT INTO user VALUES (DEFAULT, ?, ?, ?, ?, ?, ?,?)";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE userId='%d'";
    private static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE userId='%d'";
    private static final String UPDATE_USER_BY_ID = "UPDATE user SET userEmail='%s', userPassword='%s', userFirstName='%s', userLastName='%s', userRole='%s', userStatus='%s', userAbout='%s' WHERE userId='%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private User user;
    String sqlInsertion = null;

    @Override
    public List<User> findUsersList(String searchText) {
        con = null;
        pstmt = null;
        rs = null;
        List<User> userList = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();
            if (searchText.isEmpty()) {
                pstmt = con.prepareStatement(SELECT_ALL_FROM_USER);
            } else {
                sqlInsertion = String.format(SELECT_LIKE_FROM_USER, searchText.toLowerCase());
                pstmt = con.prepareStatement(sqlInsertion);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserFirstName(rs.getString("userFirstName"));
                user.setUserLastName(rs.getString("userLastName"));
                user.setUserRole(rs.getString("userRole"));
                user.setUserStatus(rs.getString("userStatus"));
                user.setUserAbout(rs.getString("userAbout"));
                userList.add(user);
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for users List", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return new ArrayList<>(userList);
    }

    @Override
    public boolean setNewUser(User newUser) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(INSERT_NEW_USER);
            pstmt.setString(1, newUser.getUserEmail());
            pstmt.setString(2, newUser.getUserPassword());
            pstmt.setString(3, newUser.getUserFirstName());
            pstmt.setString(4, newUser.getUserLastName());
            pstmt.setString(5, newUser.getUserRole());
            pstmt.setString(6, newUser.getUserStatus());
            pstmt.setString(7, newUser.getUserAbout());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed add new user to  DB", ex);
            rollback(con);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public User getUserByID(int id) {
        con = null;
        pstmt = null;
        user = new User();
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            sqlInsertion = String.format(GET_USER_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserFirstName(rs.getString("userFirstName"));
                user.setUserLastName(rs.getString("userLastName"));
                user.setUserRole(rs.getString("userRole"));
                user.setUserStatus(rs.getString("userStatus"));
                user.setUserAbout(rs.getString("userAbout"));
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for user by ID", ex);
        } finally {

            close(rs);
            close(pstmt);
            close(con);
        }
        return user;
    }

    @Override
    public boolean deleteUserByID(int id) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(DELETE_USER_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed delete user by ID from DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public Boolean updateUser(User newUser) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(UPDATE_USER_BY_ID, newUser.getUserEmail()
                    , newUser.getUserPassword(), newUser.getUserFirstName(), newUser.getUserLastName()
                    , newUser.getUserRole(), newUser.getUserStatus(), newUser.getUserAbout(), newUser.getUserId());
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to update user by ID in DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public Optional<User> authUser(String email) {
        con = null;
        pstmt = null;
        rs = null;
        sqlInsertion = null;
        user = new User();
        boolean found = false;
        try {
            con = DataBaseManager.getInstance().getConnection();
            sqlInsertion = String.format(FIND_ALL_FROM_USER_EMAIL, email);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUserEmail(rs.getString("userEmail"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setUserRole(rs.getString("userRole"));
                user.setUserStatus(rs.getString("userStatus"));
                found = true;
            }
        } catch (
                SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for user", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        if (found) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
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
