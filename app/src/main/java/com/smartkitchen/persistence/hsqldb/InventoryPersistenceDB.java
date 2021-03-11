package com.smartkitchen.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBInventory;

public class InventoryPersistenceDB implements IDBInventory {

    private final String dbPath;
    private ListValidation validation;


    public InventoryPersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Item constructItem(final ResultSet rs) throws SQLException {
        final String itemName = rs.getString("NAME");
        final int itemQuantity = rs.getInt("QUANTITY");
        final String itemUnits = rs.getString("UNIT");
        return new Item(itemName, itemQuantity, itemUnits);
    }

    @Override
    public void addToInventory(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("INSERT INTO INVENTORY_ITEMS VALUES(?, ?, ?)");
                st.setString(1, item.getName());
                st.setString(2, item.getQuantityString());
                st.setString(3, item.getUnits());

                st.executeUpdate();

                //this should return a boolean or the object it self so we can display the result of the operation in a toast
            } catch (final SQLException e) {
                //throw new PersistenceException(e);
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Item removeFromInventory(Item item) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM INVENTORY_ITEMS WHERE itemName = ?");
            sc.setString(1, item.getName());
            sc.executeUpdate();
            return item;
        } catch (final SQLException e) {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Item> getInventoryList() {
        final ArrayList<Item> inventoryList = new ArrayList<>();
        System.out.println("Test 1");
        try (Connection c = connection()) {
            System.out.println("Test 2");
            //the following query needs to be modified for the final db implementation
            final PreparedStatement st = c.prepareStatement("SELECT * FROM INVENTORY_ITEMS");
            System.out.println("Test 3");
            final ResultSet rs = st.executeQuery();
            System.out.println("Test 4");
            while (rs.next())
            {
                System.out.println("Test 5");
                final Item item = constructItem(rs);
                inventoryList.add(item);
            }
            rs.close();
            st.close();

            return inventoryList;
        }
        catch (final SQLException e)
        {
            System.out.println("Test 6");
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        System.out.println("Test 7");
        return inventoryList;
    }

    @Override
    public Item getInventoryItemByName(String name) {
        return null;
    }
}

