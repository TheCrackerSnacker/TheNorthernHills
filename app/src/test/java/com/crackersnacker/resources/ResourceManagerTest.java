package com.crackersnacker.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ResourceManagerTest {

    ResourceManager manager;

    @BeforeEach
    void setup() {
        manager = new ResourceManager();
    }

    @Test
    void loadsResource() {
        StringEntry entry = new StringEntry("Test");
        String resource = manager.get(entry);

        assert "Test-loaded".equals(resource);
    }

    @Test
    void loadsResourceOnce() {
        StringEntry entry = mock(StringEntry.class);
        when(entry.loadResource()).thenReturn("Test-loaded");
        manager.get(entry);
        manager.get(entry);

        verify(entry, times(1)).loadResource();
    }

    record StringEntry(String name) implements ResourceManager.Entry<String> {

        @Override
        public String loadResource() {
            return name + "-loaded";
        }
    }
}
