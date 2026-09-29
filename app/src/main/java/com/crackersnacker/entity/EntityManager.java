package com.crackersnacker.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    List<Entity> entities;
    List<Entity> entitiesToAdd;

    public EntityManager() {
        entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
    }

    public void update(double dt) {
        for (var entity : entitiesToAdd) {
            entity.start();
        }

        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();

        for (var entity : entities) {
            entity.update(dt);
        }

        entities.removeIf((Entity it) -> {
            if (it.isMarkedForRemoval()) {
                it.destroy();
                return true;
            }
            return false;
        });
    }

    private void registerEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    public EntityBuilder buildEntity() {
        return new ManagedEntityBuilder(this);
    }

    private static class ManagedEntityBuilder extends EntityBuilder {
        private final EntityManager manager;

        ManagedEntityBuilder(EntityManager manager) {
            super();
            this.manager = manager;
        }

        @Override
        public Entity getResult() {
            manager.registerEntity(entity);
            return super.getResult();
        }
    }
}
