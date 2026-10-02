package com.crackersnacker.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Entity {
    private final List<Script> scripts;

    private double x, y;
    private boolean initialized = false;
    private boolean markedForRemoval = false;
    private boolean destroyed = false;

    Entity() {
        scripts = new ArrayList<>();
    }

    void addScript(Script script) {
        if (initialized) {
            throw new IllegalStateException("Cannot add Script to initialized Entity.");
        }
        if (script.getEntity() != null) {
            throw new IllegalStateException("Cannot add same Script to multiple Entities.");
        }
        scripts.add(script);
        script.setEntity(this);
    }

    @SuppressWarnings("unchecked")
    <T extends Script> T getScript(Class<T> type) {
        for (Script script : scripts) {
            if (type.isAssignableFrom(script.getClass())) {
                return (T)script;
            }
        }
        throw new IllegalArgumentException("Required script of type '"+type.getName()+"' is not present on this Entity.");
    }

    @SuppressWarnings("unchecked")
    <T extends Script> Optional<T> getOptionalScript(Class<T> type) {
        for (Script script : scripts) {
            if (type.isAssignableFrom(script.getClass())) {
                return Optional.of((T)script);
            }
        }
        return Optional.empty();
    }

    void init() {
        if (initialized) {
            throw new IllegalStateException("Cannot initialize an entity that has already been initialized.");
        }
        initialized = true;
        for (Script script : scripts) {
            script.init();
        }
    }

    void start() {
        for (Script script : scripts) {
            script.start();
        }
    }

    public void update(double dt) {
        for (Script script : scripts) {
            if (script.isEnabled()) {
                script.update(dt);
            }
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double newX) {
        x = newX;
    }

    public double getY() {
        return y;
    }

    public void setY(double newY) {
        y = newY;
    }

    boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    void markForRemoval() {
        markedForRemoval = true;
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void destroy() {
        for (Script script : scripts) {
            script.onDestroy();
            script.destroy();
        }
        scripts.clear();
        this.destroyed = true;
    }
}
