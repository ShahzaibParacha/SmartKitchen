package com.smartkitchen.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Item;
import com.smartkitchen.persistence.IDBInventory;

public class InventoryPersistenceDB implements IDBInventory {

    private final String dbPath;
    private ListValidation validation;
    private static List<Item> inventory;


    public InventoryPersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
        this.inventory = new ArrayList<>();
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Item constructItem(final ResultSet rs) throws SQLException {
        final String itemName = rs.getString("NAME");
        final int itemQuantity = rs.getInt("QUANTITY");
        final String itemUnits = rs.getString("UNIT");
        final int itemQuantityToBuy = rs.getInt("QUANTITY_TO_BUY");
        final int itemThresholdQuantity = rs.getInt("THRESHOLD_QUANTITY");
        final int
        final int itemID = rs.getInt("ITEM_ID")
        return new Item(itemName, itemQuantity, itemUnits, itemQuantityToBuy, itemThresholdQuantity, itemID);
    }

    private ArrayList<String>

    @Override
    public void addToInventory(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("INSERT INTO INVENTORY_ITEMS VALUES(?, ?, ?, ?, ?)");
                st.setString(1, item.getName());
                st.setInt(2, item.getQuantity());
                st.setString(3, item.getUnits());
                st.setInt(4, item.getQuantityToBuy());
                st.setInt(5, item.getThresholdQuantity());

                st.executeUpdate();
                inventory.add(item);

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
            final PreparedStatement sc = c.prepareStatement("DELETE FROM INVENTORY_ITEMS WHERE NAME = ?");
            sc.setString(1, item.getName());
            sc.executeUpdate();

            inventory.remove(item);

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

        try (Connection c = connection()) {
            //the following query needs to be modified for the final db implementation
            final PreparedStatement st = c.prepareStatement("SELECT * FROM INVENTORY_ITEMS");

            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Item item = constructItem(rs);
                inventoryList.add(item);
            }
            rs.close();
            st.close();

            return inventoryList;
        }
        catch (final SQLException e)
        {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return inventoryList;
    }

    @Override
    public Item getInventoryItemByName(String name) {
        return null;
    }
}

