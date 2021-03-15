package com.smartkitchen.tests.objects;

import org.junit.Test;

import com.smartkitchen.objects.Allergies;
import com.smartkitchen.objects.Item;
import static org.junit.Assert.*;

public class AllergiesTest{

    @Test
    public void testAllergies(){
        Allergies testAllergies = new Allergies();

        System.out.println("\nStarting testAllergies.");

        //Assert
        assertEquals(testAllergies.LACTOSE, "Lactose");
        assertEquals(testAllergies.EGGS, "Eggs");
        assertEquals(testAllergies.GLUTEN, "Gluten");
        assertEquals(testAllergies.NUTS, "Nuts");
        assertEquals(testAllergies.FISH, "Fish");
        assertEquals(testAllergies.SOY, "Soy");

        System.out.println("Finished testAllergies.");
    }

}