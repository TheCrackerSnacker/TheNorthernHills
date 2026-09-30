package com.crackersnacker.entity;

public class EntityBuilder {
    protected final Entity entity;
    private boolean finalized;

    EntityBuilder() {
        entity = new Entity();
        finalized = false;
    }

    public EntityBuilder withScript(Script script) {
        if (isFinalized()) throw new IllegalStateException("Cannot add Script. Entity construction has already been finalized.");
        entity.addScript(script);
        return this;
    }

    public EntityBuilder withPos(float x, float y) {
        if (isFinalized()) throw new IllegalStateException("Cannot change position. Entity construction has already been finalized.");
        entity.setX(x);
        entity.setY(y);
        return this;
    }

    protected boolean isFinalized() {
        return finalized;
    }

    public Entity getResult() {
        if (!finalized) {
            entity.init();
        }
        finalized = true;
        return entity;
    }
}
