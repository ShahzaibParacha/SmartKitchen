package com.smartkitchen.persistence.hsqldb;

import android.util.Log;

import com.smartkitchen.business.IListValidation;
import com.smartkitchen.business.InvalidInputException;
import com.smartkitchen.business.ListValidation;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.IDBRecipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePersistenceDB implements IDBRecipe {
    private final String dbPath;
    private IListValidation validation;
    private static List<Recipe> recipes;


    public RecipePersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
        this.recipes = new ArrayList<>();
        this.validation = new ListValidation();
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Recipe constructRecipe(final ResultSet rs) throws SQLException {
        String recipeName = rs.getString("NAME");
        ArrayList<String> ingredients = new ArrayList<>();
        if(rs.getString("INGREDIENTS") != null)
            ingredients = stringToList(rs.getString("INGREDIENTS"));
        ArrayList<String> ingredientQuantities = new ArrayList<>();
        if(rs.getString("INGREDIENT_QUANTITIES") != null)
            ingredientQuantities = stringToList(rs.getString("INGREDIENT_QUANTITIES"));
        ArrayList<String> ingredientUnits = new ArrayList<>();
        if(rs.getString("INGREDIENT_UNITS") != null)
            ingredientUnits = stringToList(rs.getString("INGREDIENT_UNITS"));
        ArrayList<String> steps = new ArrayList<>();
        if(rs.getString("STEPS") != null)
            steps = stringToList(rs.getString("STEPS"));
        final int itemID = rs.getInt("ITEM_ID");
        Recipe recipe = new Recipe(recipeName, ingredients, ingredientQuantities, ingredientUnits, steps);
        recipe.setId(itemID);
        return recipe;
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
    public void addToRecipes(Recipe recipe ) {
        try {
            validation.containsRecipeInputs(recipe);
            try (final Connection c = connection()) {
                final PreparedStatement st = c.prepareStatement("INSERT INTO RECIPE_ITEMS VALUES(DEFAULT, ?, ?, ?, ?, ?)");
                st.setString(1, recipe.getName());
                st.setString(2, listToString(recipe.getIngredients()));
                st.setString(3, listToString(recipe.getIngredientQuantities()));
                st.setString(4, listToString(recipe.getIngredientUnits()));
                st.setString(5, listToString(recipe.getSteps()));

                st.executeUpdate();
                recipes.add(recipe);

                //this should return a boolean or the object it self so we can display the result of the operation in a toast
            } catch (final SQLException e) {
                //throw new PersistenceException(e);
                System.out.println(e.getMessage());
            }
        }
        catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Recipe removeRecipe(Recipe recipe) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM RECIPE_ITEMS WHERE ITEM_ID = ?");
            sc.setInt(1, recipe.getId());
            sc.executeUpdate();

            recipes.remove(recipe);

            return recipe;
        } catch (final SQLException e) {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Recipe> getRecipeList() {
        final ArrayList<Recipe> recipeList = new ArrayList<>();

        try (Connection c = connection()) {
            //the following query needs to be modified for the final db implementation
            final PreparedStatement st = c.prepareStatement("SELECT * FROM RECIPE_ITEMS");

            final ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                final Recipe recipe = constructRecipe(rs);
                recipeList.add(recipe);
            }
            rs.close();
            st.close();

            return recipeList;
        }
        catch (final SQLException e)
        {
            //throw new PersistenceException(e);
            System.out.println(e.getMessage());
        }
        return recipeList;
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE RECIPE_ITEMS set NAME = ?, INGREDIENTS = ?, INGREDIENT_QUANTITIES = ?, INGREDIENT_UNITS = ?, STEPS = ? WHERE ITEM_ID = ?");
            st.setString(1, recipe.getName());
            st.setString(2, listToString(recipe.getIngredients()));
            st.setString(3, listToString(recipe.getIngredientQuantities()));
            st.setString(4, listToString(recipe.getIngredientUnits()));
            st.setString(5, listToString(recipe.getSteps()));

            st.executeUpdate();
            st.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

}
