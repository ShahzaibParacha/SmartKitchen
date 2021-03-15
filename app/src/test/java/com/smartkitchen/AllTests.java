package com.smartkitchen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.smartkitchen.tests.objects.*;
import com.smartkitchen.tests.business.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemTest.class,
        ListValidationTest.class,
        AllergiesTest.class,
        ListActionsTest.class,
})
public class AllTests { }
