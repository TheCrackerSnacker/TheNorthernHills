package com.crackersnacker.resources;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private final Map<Entry<?>, Object> directory;

    public ResourceManager() {
        directory = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Entry<T> entry) {
        return (T)directory.computeIfAbsent(entry, this::load);
    }

    private Object load(Entry<?> entry) {
        return entry.loadResource();
    }

    public interface Entry<T> {
        /** Calling this method loads and returns the resource provided by this {@link Entry}.
         * It is recommended to use a dedicated management system like that provided by
         * {@link ResourceManager}.**/
        T loadResource();
    }
}
