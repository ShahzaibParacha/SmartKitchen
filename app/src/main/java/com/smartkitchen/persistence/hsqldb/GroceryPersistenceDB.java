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
import com.smartkitchen.persistence.IDBGrocery;

public class GroceryPersistenceDB implements IDBGrocery {

    private final String dbPath;
    private ListValidation validation;
    private static List<Item> grocery;


    public GroceryPersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
        grocery = new ArrayList<>();
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
        return new Item(itemName, itemQuantity, itemUnits, itemQuantityToBuy, itemThresholdQuantity);
    }

    @Override
    public void addToGrocery(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("INSERT INTO GROCERY_ITEMS VALUES(?, ?, ?, ?, ?)");
                st.setString(1, item.getName());
                st.setInt(2, item.getQuantity());
                st.setString(3, item.getUnits());
                st.setInt(4, item.getQuantityToBuy());
                st.setInt(5, item.getThresholdQuantity());

                st.executeUpdate();
                grocery.add(item);

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
    public Item removeFromGrocery(Item item) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM GROCERY_ITEMS WHERE NAME = ?");
            sc.setString(1, item.getName());
            sc.executeUpdate();

            grocery.remove(item);

            return item;
        } catch (final SQLException e) {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Item> getGroceryList() {
        final ArrayList<Item> groceryList = new ArrayList<>();

        try (Connection c = connection()) {
            //the following query needs to be modified for the final db implementation
            final PreparedStatement st = c.prepareStatement("SELECT * FROM GROCERY_ITEMS");

            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Item item = constructItem(rs);
                groceryList.add(item);
            }
            rs.close();
            st.close();

            return groceryList;
        }
        catch (final SQLException e)
        {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return groceryList;
    }

    @Override
    public Item getGroceryItemByName(String name) {
        return null;
    }
}

