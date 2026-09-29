package com.crackersnacker.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static com.crackersnacker.entity.EntityUtils.destroy;

public class EntityManagerTest {
    EntityManager manager;

    @BeforeEach
    void setup() {
        manager = new EntityManager();
    }

    @Test
    void buildsAndUpdatesEntity() {
        Script script = mock(Script.class);
        when(script.isEnabled()).thenReturn(true);
        manager.buildEntity().withScript(script).getResult();
        manager.update(0.05);
        verify(script, times(1)).init();
        verify(script, times(1)).start();
        verify(script, times(1)).update(0.05);
    }

    @Test
    void destroysEntity() {
        Script script = mock(Script.class);
        when(script.isEnabled()).thenReturn(true);
        Entity entity = manager.buildEntity().withScript(script).getResult();
        destroy(entity);
        manager.update(0.05);
        manager.update(0.05); // Second update should fire as Script is destroyed
        verify(script, times(1)).update(0.05);
        verify(script, times(1)).onDestroy();
    }
}
