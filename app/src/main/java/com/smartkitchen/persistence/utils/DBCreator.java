package com.smartkitchen.persistence.utils;

import com.smartkitchen.application.Initialize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DBCreator {
    private final String dbPath;
    public DBCreator() {
        this.dbPath = Initialize.getDBPathName();
        createGroceryDB();
        createInventoryDB();
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + dbPath + ";shutdown=true", "SA", "");
    }
    private void createGroceryDB() {
        try(Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement( "CREATE MEMORY TABLE PUBLIC.GROCERY_ITEMS(" +
                    "NAME VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "QUANTITY INTEGER,UNIT VARCHAR(20)," +
                    " QUANTITY_TO_BUY INTEGER," +
                    " THRESHOLD_QUANTITY INTEGER," +
                    " ALLERGIES VARCHAR(100)," +
                    " CALORIES INTEGER, PRICE DOUBLE)");
            st.executeUpdate();
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void createInventoryDB() {
        try(Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement( "CREATE MEMORY TABLE PUBLIC.INVENTORY_ITEMS(" +
                    "NAME VARCHAR(50) NOT NULL PRIMARY KEY," +
                    "QUANTITY INTEGER,UNIT VARCHAR(20)," +
                    " QUANTITY_TO_BUY INTEGER," +
                    " THRESHOLD_QUANTITY INTEGER," +
                    " ALLERGIES VARCHAR(100)," +
                    " CALORIES INTEGER," +
                    " PRICE DOUBLE)");
            st.executeUpdate();
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
