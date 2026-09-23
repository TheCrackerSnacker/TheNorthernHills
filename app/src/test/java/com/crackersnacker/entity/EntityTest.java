package com.crackersnacker.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class EntityTest {
    Entity entity;

    @BeforeEach
    void setup() {
        entity = new Entity();
    }

    @Test
    void returnsEmptyOptionalForNonexistentScript() {
        Optional<Script> opt = entity.getOptionalScript(Script.class);
        assert(opt.isEmpty());
    }

    @Test
    void returnsExistentScript() {
        Script script = new Script();
        entity.addScript(script);
        Script returnedScript = entity.getScript(Script.class);
        assertEquals(script, returnedScript);
    }

    @Test
    void throwsOnMissingRequiredScript() {
        assertThrows(IllegalStateException.class, () -> entity.getScript(Script.class));
    }

    @Test
    void throwsOnAlreadyOwnedScript() {
        Entity entity2 = new Entity();
        Script script = new Script();
        entity2.addScript(script);
        assertThrows(IllegalStateException.class, () -> entity.addScript(script));
    }

    @Test
    void throwsOnAddingToInitialized() {
        Script script = new Script();
        entity.init();
        assertThrows(IllegalStateException.class, () -> entity.addScript(script));
    }
}
