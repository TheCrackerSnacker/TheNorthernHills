package com.crackersnacker.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Entity {
    float x, y;
    List<Script> scripts;
    boolean initialized = false;

    public Entity() {
        scripts = new ArrayList<Script>();
    }

    public void addScript(Script script) {
        if (initialized) {
            throw new IllegalStateException("Cannot add same Script to initialized Entity.");
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
        throw new IllegalStateException("Required script of type '"+type.getName()+"' is not present on this Entity.");
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

    public void init() {
        if (initialized) {
            throw new IllegalStateException("Cannot initialize an entity that has already been initialized.");
        }
        initialized = true;
        for (Script script : scripts) {
            script.init();
        }
    }

    public void update() {
        for (Script script : scripts) {
            if (script.isEnabled()) {
                script.update();
            }
        }
    }
}
