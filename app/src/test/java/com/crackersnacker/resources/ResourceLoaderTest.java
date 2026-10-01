package com.crackersnacker.resources;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceLoaderTest {

    @Test
    void loadsExistentTextFile() {
        try {
            assertEquals("Hello, World!\n", ResourceLoader.loadTextResource("file.txt"));
        } catch (IOException e) {
            fail("Threw exception when loading real file.");
        }
    }

    @Test
    void throwsOnNonexistentFile() {
        assertThrows(IOException.class, () -> ResourceLoader.loadTextResource("does_not_exist.txt"));
    }
}
