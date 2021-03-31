package com.smartkitchen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.smartkitchen.tests.objects.*;
import com.smartkitchen.tests.business.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemTest.class,
        RecipeTest.class,
        ListValidationTest.class,
        ListActionsTest.class,
        RecipeActionsTest.class,
})
public class AllUnitTests { }
