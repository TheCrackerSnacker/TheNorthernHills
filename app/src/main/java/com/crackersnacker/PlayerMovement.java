package com.crackersnacker;

import com.crackersnacker.entity.Script;

public class PlayerMovement extends Script {
    private final Input input;
    private final double speed = 0.5;

    public PlayerMovement(Input input) {
        this.input = input;
    }

    @Override
    protected void update(double deltaTime) {
        if (input.getKeyDown("Left")) {
            getEntity().setX(getEntity().getX() - speed * deltaTime);
        }
        if (input.getKeyDown("Right")) {
            getEntity().setX(getEntity().getX() + speed * deltaTime);
        }
        if (input.getKeyDown("Up")) {
            getEntity().setY(getEntity().getY() + speed * deltaTime);
        }
        if (input.getKeyDown("Down")) {
            getEntity().setY(getEntity().getY() - speed * deltaTime);
        }
    }
}
