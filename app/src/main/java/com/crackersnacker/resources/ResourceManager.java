package com.crackersnacker.resources;

import java.util.HashMap;
import java.util.Map;

public abstract class ResourceManager<K, V> {

    private final Map<K, V> directory;

    public ResourceManager() {
        directory = new HashMap<>();
    }

    public V get(K entry) {
        return directory.computeIfAbsent(entry, this::load);
    }

    protected abstract V load(K entry);
}
