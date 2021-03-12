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
        String itemName = rs.getString("NAME");
        int itemQuantity = rs.getInt("QUANTITY");
        String itemUnits = rs.getString("UNIT");
        int itemQuantityToBuy = rs.getInt("QUANTITY_TO_BUY");
        int itemThresholdQuantity = rs.getInt("THRESHOLD_QUANTITY");
        ArrayList<String> itemAllergies = new ArrayList<>();
        if(rs.getString("ALLERGIES") != null)
            itemAllergies = stringToList(rs.getString("ALLERGIES"));
        int itemCaloriesPerUnit = rs.getInt("CALORIES_PER_UNIT");
        double itemPricePerUnit = rs.getDouble("PRICE_PER_UNIT");
        final int itemID = rs.getInt("ITEM_ID");
        Item item = new Item(itemName, itemQuantity, itemUnits, itemQuantityToBuy, itemThresholdQuantity, itemAllergies, itemCaloriesPerUnit, itemPricePerUnit);
        item.setId(itemID);
        return item;
    }

    private ArrayList<String> stringToList(String allergies) {
        ArrayList<String> allergiesList = new ArrayList<>();
        if (!allergies.equals("")) {
            String[] parsedAllergies = allergies.split(",");
            for (String s : parsedAllergies) {
                allergiesList.add(s);
            }
        }
        return allergiesList;
    }

    private String listToString(ArrayList<String> allergiesList) {
        String allergies = "";
        for (int i = 0; i < allergiesList.size(); i++) {
            if (i < allergiesList.size()-1)
                allergies += allergiesList.get(i) + ",";
            else
                allergies += allergiesList.get(i);
        }
        return allergies;
    }

    @Override
    public void addToGrocery(Item item) {
        validation = new ListValidation(item);
        try {
            validation.containsItemInputs();
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("INSERT INTO GROCERY_ITEMS VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
                st.setString(1, item.getName());
                st.setInt(2, item.getQuantity());
                st.setString(3, item.getUnits());
                st.setInt(4, item.getQuantityToBuy());
                st.setInt(5, item.getThresholdQuantity());
                st.setString(6, listToString(item.getAllergies()));
                st.setInt(7, item.getCaloriesPerUnit());
                st.setDouble(8, item.getPricePerUnit());

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
            final PreparedStatement sc = c.prepareStatement("DELETE FROM GROCERY_ITEMS WHERE ITEM_ID = ?");
            sc.setInt(1, item.getId());
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
    public void updateItem(Item item) {

    }
}

