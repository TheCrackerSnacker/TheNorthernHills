package com.crackersnacker.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityBuilderTest {
    EntityBuilder builder;

    @BeforeEach
    void setup() {
        builder = new EntityBuilder()
                .withScript(new Script())
                .withPos(42f, 42f);
    }

    @Test
    void buildsAndInitializesEntity() {
        Entity entity = builder.getResult();
        assertThrows(IllegalStateException.class, entity::init);
    }

    @Test
    void throwsOnModifyAfterFinalize() {
        Entity entity = builder.getResult();
        assertAll(
                () -> assertThrows(IllegalStateException.class, () -> builder.withScript(new Script())),
                () -> assertThrows(IllegalStateException.class, () -> builder.withPos(67, -67))
        );
    }

    @Test
    void returnsSameEntityOnMultiFinalize() {
        Entity entity1 = builder.getResult();
        Entity entity2 = builder.getResult();
        assertEquals(entity1, entity2);
    }
}
