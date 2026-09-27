package com.crackersnacker.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void updatesEnabledScriptsOnly() {
        Script script = mock(Script.class);
        Script disabledScript = mock(Script.class);
        when(script.isEnabled()).thenReturn(true);
        disabledScript.setEnabled(false);
        when(disabledScript.isEnabled()).thenReturn(false);
        entity.addScript(script);
        entity.addScript(disabledScript);
        entity.update();
        verify(script, times(1)).update();
        verify(disabledScript, times(0)).update();
    }

    @Test
    void initializesEnabledAndDisabledScripts() {
        Script script = mock(Script.class);
        Script disabledScript = mock(Script.class);
        when(script.isEnabled()).thenReturn(true);
        disabledScript.setEnabled(false);
        when(disabledScript.isEnabled()).thenReturn(false);
        entity.addScript(script);
        entity.addScript(disabledScript);
        entity.init();
        verify(script, times(1)).init();
        verify(disabledScript, times(1)).init();
    }
}
