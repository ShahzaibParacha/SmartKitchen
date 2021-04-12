package com.smartkitchen.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBInventory;

//HSQLDB implementation of the inventory database
public class InventoryPersistenceDB implements IDBInventory {

    private final String dbPath;
    private final IListActions listActions = new ListActions();

    //Access Database at the path
    public InventoryPersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    //Connect the application to the database
    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    //Builds an inventory item based on the database information
    private Item constructItem(final ResultSet rs) throws SQLException {
        String itemName = rs.getString("NAME");
        int itemQuantity = rs.getInt("QUANTITY");
        String itemUnits = rs.getString("UNIT");
        int itemQuantityToBuy = rs.getInt("QUANTITY_TO_BUY");
        int itemThresholdQuantity = rs.getInt("THRESHOLD_QUANTITY");
        ArrayList<String> itemAllergies = new ArrayList<>();
        if (rs.getString("ALLERGIES") != null)
            itemAllergies = listActions.stringToList(rs.getString("ALLERGIES"));
        int itemCaloriesPerUnit = rs.getInt("CALORIES_PER_UNIT");
        double itemPricePerUnit = rs.getDouble("PRICE_PER_UNIT");
        final int itemID = rs.getInt("ITEM_ID");
        Item item = new Item(itemName, itemQuantity, itemUnits, itemQuantityToBuy, itemThresholdQuantity, itemAllergies, itemCaloriesPerUnit, itemPricePerUnit);
        item.setId(itemID);
        return item;
    }

    //Adds an item to the database
    @Override
    public void addToInventory(Item item) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO INVENTORY_ITEMS VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, item.getName());
            st.setInt(2, item.getQuantity());
            st.setString(3, item.getUnits());
            st.setInt(4, item.getQuantityToBuy());
            st.setInt(5, item.getThresholdQuantity());
            st.setString(6, listActions.listToString(item.getAllergies()));
            st.setInt(7, item.getCaloriesPerUnit());
            st.setDouble(8, item.getPricePerUnit());

            st.executeUpdate();

        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Removes an item from the database
    @Override
    public void removeFromInventory(Item item) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM INVENTORY_ITEMS WHERE ITEM_ID = ?");
            sc.setInt(1, item.getId());
            sc.executeUpdate();

        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Returns the entire inventory list in ArrayList form
    @Override
    public ArrayList<Item> getInventoryList() {
        final ArrayList<Item> inventoryList = new ArrayList<>();
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM INVENTORY_ITEMS");

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Item item = constructItem(rs);
                inventoryList.add(item);
            }
            rs.close();
            st.close();

            return inventoryList;
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventoryList;
    }

    //Updates an item in the db, matches based on id
    @Override
    public void updateItem(Item item) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE INVENTORY_ITEMS set NAME = ?, QUANTITY = ?, UNIT = ?, QUANTITY_TO_BUY = ?, THRESHOLD_QUANTITY = ?, ALLERGIES = ?, CALORIES_PER_UNIT = ?, PRICE_PER_UNIT = ? WHERE ITEM_ID = ?");
            st.setString(1, item.getName());
            st.setInt(2, item.getQuantity());
            st.setString(3, item.getUnits());
            st.setInt(4, item.getQuantityToBuy());
            st.setInt(5, item.getThresholdQuantity());
            st.setString(6, listActions.listToString(item.getAllergies()));
            st.setInt(7, item.getCaloriesPerUnit());
            st.setDouble(8, item.getPricePerUnit());
            st.setInt(9, item.getId());

            st.executeUpdate();
            st.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

}

