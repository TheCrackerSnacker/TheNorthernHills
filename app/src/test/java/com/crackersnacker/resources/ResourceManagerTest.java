package com.crackersnacker.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ResourceManagerTest {

    StringManager manager;

    @BeforeEach
    void setup() {
        manager = new StringManager();
    }

    @Test
    void loadsResource() {
        String entry = "Test";
        String resource = manager.get(entry);

        assert "Test-loaded".equals(resource);
    }

    @Test
    void loadsEachResourceOnce() {
        manager.get("Test");
        manager.get("Test");
        assert manager.getLoadCount() == 1;
        manager.get("Test2");
        assert manager.getLoadCount() == 2;
    }

    static class StringManager extends ResourceManager<String, String> {
        private int loadCounter = 0;

        @Override
        protected String load(String entry) {
            loadCounter++;
            return entry + "-loaded";
        }

        public int getLoadCount() {
            return loadCounter;
        }
    }
}
