package com.crackersnacker.entity;

public class EntityUtils {

    public static boolean isNull(Entity entity) {
        return entity == null || entity.isDestroyed();
    }

    public static boolean isNull(Script script) {
        return script == null || script.isDestroyed();
    }

    public static void destroy(Entity entity) {
        if (entity != null) {
            entity.markForRemoval();
        }
    }
}
