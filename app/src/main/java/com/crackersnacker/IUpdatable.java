package com.crackersnacker;

interface IUpdatable {
    public default void setUpObject() {}
    public void updateObject(float deltaTime);
    public default void cleanUpObject() {}
}