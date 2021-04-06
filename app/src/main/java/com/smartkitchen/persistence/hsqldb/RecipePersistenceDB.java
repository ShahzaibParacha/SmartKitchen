package com.smartkitchen.persistence.hsqldb;

import android.util.Log;

import com.smartkitchen.business.implementation.ListActions;
import com.smartkitchen.business.interfaces.IListActions;
import com.smartkitchen.objects.Recipe;
import com.smartkitchen.persistence.IDBRecipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//HSQLDB implementation of the recipe database
public class RecipePersistenceDB implements IDBRecipe {

    private final String dbPath;
    private final IListActions listActions = new ListActions();

    //Access database at the path
    public RecipePersistenceDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    //Connect the application to the database
    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    //Builds the recipe object based on database information
    private Recipe constructRecipe(final ResultSet rs) throws SQLException {
        String recipeName = rs.getString("NAME");
        System.out.println("Name of recipe 1: " + recipeName);
        ArrayList<String> ingredients = new ArrayList<>();
        if (rs.getString("INGREDIENTS") != null)
            ingredients = listActions.stringToList(rs.getString("INGREDIENTS"));
        ArrayList<String> ingredientQuantities = new ArrayList<>();
        if (rs.getString("INGREDIENT_QUANTITIES") != null)
            ingredientQuantities = listActions.stringToList(rs.getString("INGREDIENT_QUANTITIES"));
        ArrayList<String> ingredientUnits = new ArrayList<>();
        if (rs.getString("INGREDIENT_UNITS") != null)
            ingredientUnits = listActions.stringToList(rs.getString("INGREDIENT_UNITS"));
        ArrayList<String> steps = new ArrayList<>();
        if (rs.getString("STEPS") != null)
            steps = listActions.stringToList(rs.getString("STEPS"));
        final int itemID = rs.getInt("ITEM_ID");
        Recipe recipe = new Recipe(recipeName, ingredients, ingredientQuantities, ingredientUnits, steps);
        recipe.setId(itemID);
        return recipe;
    }

    //Adds a recipe to the database
    @Override
    public void addToRecipes(Recipe recipe) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO RECIPE_ITEMS VALUES(DEFAULT, ?, ?, ?, ?, ?)");
            st.setString(1, recipe.getName());
            st.setString(2, listActions.listToString(recipe.getIngredients()));
            st.setString(3, listActions.listToString(recipe.getIngredientQuantities()));
            st.setString(4, listActions.listToString(recipe.getIngredientUnits()));
            st.setString(5, listActions.listToString(recipe.getInstructions()));

            st.executeUpdate();

        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Removes a recipe from the database
    @Override
    public void removeRecipe(Recipe recipe) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM RECIPE_ITEMS WHERE ITEM_ID = ?");
            sc.setInt(1, recipe.getId());
            sc.executeUpdate();

        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Returns the entire recipe database in ArrayList form
    @Override
    public ArrayList<Recipe> getRecipeList() {
        final ArrayList<Recipe> recipeList = new ArrayList<>();

        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM RECIPE_ITEMS");

            final ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Recipe recipe = constructRecipe(rs);
                recipeList.add(recipe);
            }
            rs.close();
            st.close();

            return recipeList;
        } catch (final SQLException e) {
            System.out.println(e.getMessage());
        }
        return recipeList;
    }

    //Updates a recipe in the database, matches based on id
    @Override
    public void updateRecipe(Recipe recipe) {
        try (Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE RECIPE_ITEMS set NAME = ?, INGREDIENTS = ?, INGREDIENT_QUANTITIES = ?, INGREDIENT_UNITS = ?, STEPS = ? WHERE ITEM_ID = ?");
            st.setString(1, recipe.getName());
            st.setString(2, listActions.listToString(recipe.getIngredients()));
            st.setString(3, listActions.listToString(recipe.getIngredientQuantities()));
            st.setString(4, listActions.listToString(recipe.getIngredientUnits()));
            st.setString(5, listActions.listToString(recipe.getInstructions()));
            st.setInt(6, recipe.getId());

            st.executeUpdate();
            st.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            System.out.println(e.getMessage());
        }
    }

}

