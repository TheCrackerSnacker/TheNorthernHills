package com.crackersnacker.entity;

public class EntityBuilder {
    private final Entity entity;
    private boolean finalized;

    public EntityBuilder() {
        entity = new Entity();
        finalized = false;
    }

    public EntityBuilder withScript(Script script) {
        if (finalized) throw new IllegalStateException("Cannot add Script. Entity construction has already been finalized.");
        entity.addScript(script);
        return this;
    }

    public EntityBuilder withPos(float x, float y) {
        if (finalized) throw new IllegalStateException("Cannot change position. Entity construction has already been finalized.");
        entity.setX(x);
        entity.setY(y);
        return this;
    }

    public Entity getResult() {
        if (!finalized) {
            entity.init();
        }
        finalized = true;
        return entity;
    }
}
