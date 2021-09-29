package com.tracker.impl.admin.category;

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

public class CategoryRepositorySQLImpl implements CategoryRepository {
    private static final Logger log = LogManager.getLogger(CategoryRepositorySQLImpl.class);

    private static final String SELECT_ALL_FROM_CATEGORY = "SELECT * FROM activity_category";
    private static final String SELECT_LIKE_FROM_CATEGORY = "SELECT * FROM activity_category WHERE Lower(categoryName) LIKE '%%%s%%'";
    private static final String INSERT_NEW_CATEGORY = "INSERT INTO activity_category VALUES (DEFAULT, ?, ?)";
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM activity_category WHERE categoryId='%d'";
    private static final String DELETE_CATEGORY_BY_ID = "DELETE FROM activity_category WHERE categoryId='%d'";
    private static final String UPDATE_CATEGORY_BY_ID = "UPDATE activity_category SET categoryName='%s', categoryDescription='%s'  WHERE categoryId='%d'";
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Category category;
    String sqlInsertion = null;

    @Override
    public List<Category> findCategoryList(String searchText) {
        con = null;
        pstmt = null;
        rs = null;
        List<Category> categoryList = new ArrayList<>();
        try {
            con = DataBaseManager.getInstance().getConnection();
            if (searchText.isEmpty()) {
                pstmt = con.prepareStatement(SELECT_ALL_FROM_CATEGORY);
            } else {
                sqlInsertion = String.format(SELECT_LIKE_FROM_CATEGORY, searchText.toLowerCase());
                pstmt = con.prepareStatement(sqlInsertion);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setCategoryDescription(rs.getString("categoryDescription"));
                categoryList.add(category);
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for Category List", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return categoryList;
    }

    @Override
    public boolean setCategory(Category category) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(INSERT_NEW_CATEGORY);
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getCategoryDescription());
            pstmt.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException ex) {
            rollback(con);
            log.log(Level.ERROR, "Failed add new category to  DB", ex);
        } finally {
            close(con);
        }
        return false;
    }

    @Override
    public Category GetCategory(int id) {
        con = null;
        pstmt = null;
        rs = null;
        category = new Category();
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            sqlInsertion = String.format(GET_CATEGORY_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setCategoryDescription(rs.getString("categoryDescription"));
            }
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to read DB data for category by ID", ex);
        } finally {
            close(rs);
            close(pstmt);
            close(con);
        }
        return category;
    }

    @Override
    public boolean deleteCategory(int id) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(DELETE_CATEGORY_BY_ID, id);
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed delete category by ID from DB", ex);
        } finally {
            close(pstmt);
            close(con);
        }
        return false;
    }

    @Override
    public Boolean updateCategory(Category updCategory) {
        con = null;
        pstmt = null;
        try {
            con = DataBaseManager.getInstance().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            sqlInsertion = String.format(UPDATE_CATEGORY_BY_ID, updCategory.getCategoryName(), updCategory.getCategoryDescription(), updCategory.getCategoryId());
            pstmt = con.prepareStatement(sqlInsertion);
            pstmt.executeUpdate();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            return true;
        } catch (SQLException ex) {
            log.log(Level.ERROR, "Failed to category by ID in DB", ex);
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
