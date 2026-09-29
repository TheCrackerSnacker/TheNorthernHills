package com.crackersnacker.entity;

import java.util.Optional;

public class Script {
    private Entity entity;
    private boolean enabled = true;
    private boolean destroyed = false;

    public void setEnabled(boolean enabled) {
        boolean prevEnabled = this.enabled;
        this.enabled = enabled;

        if (!prevEnabled && enabled) {
            this.onEnable();
        } else if (prevEnabled && !enabled) {
            this.onDisable();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void destroy() {
        destroyed = true;
    }

    void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }

    protected <T extends Script> T getScript(Class<T> type) {
        return entity.getScript(type);
    }

    protected <T extends Script> Optional<T> getOptionalScript(Class<T> type) {
        return entity.getOptionalScript(type);
    }

    protected void init() {}
    protected void start() {}
    protected void update(double deltaTime) {}
    protected void onEnable() {}
    protected void onDisable() {}
    protected void onDestroy() {}
}
