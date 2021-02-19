package com.smartkitchen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.smartkitchen.tests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemTest.class,
        ItemListsTest.class,

})
public class AllTests { }
