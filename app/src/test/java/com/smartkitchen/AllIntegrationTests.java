package com.smartkitchen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.smartkitchen.tests.business.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ListActionsIT.class,
        ListValidationIT.class,
        RecipeActionsIT.class,
})
public class AllIntegrationTests { }
