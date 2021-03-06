package com.smartkitchen.tests.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import com.smartkitchen.application.Initialize;

public class TestUtils{
    private static final File DB_SRC = new File("src/main/assets/db/SC.script");

    public static File copyDB() throws IOException{
        final File target = File.createTempFile("temp-db", ".script");
        //If there is an error here, delete dependency for guava and resync gradle build
        Files.copy(DB_SRC, target);
        Initialize.setDBPathName(target.getAbsolutePath().replace(".script", ""));

        return target;
    }
}