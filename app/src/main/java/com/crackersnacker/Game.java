package com.crackersnacker;

import com.crackersnacker.entity.Entity;
import com.crackersnacker.entity.EntityManager;

import java.io.IOError;
import java.io.IOException;

public class Game {
    Window window;
    ShaderManager shaderManager;
    EntityManager entityManager;
    Renderer renderer;
    Entity ent1;

    private final double UPS;

    public Game(double updatesPerSecond) {
        UPS = updatesPerSecond;
    }

    private void setup() {
        Texture tex;
        try {
            tex = new Texture("matt.png");
        } catch (IOException e) {
            throw new IOError(e);
        }

        shaderManager = new ShaderManager();
        renderer = new Renderer();

        Shader shad = shaderManager.get(ShaderManager.ShaderId.Obj);
        SpriteRenderer spriteRenderer = new SpriteRenderer(tex, shad, renderer, 0.8f, 1.0f);

        ent1 = entityManager
                .buildEntity()
                .withScript(spriteRenderer)
                .withPos(0.5f, 0.5f)
                .getResult();

        window = new Window();
    }

    public void loop() {
        setup();
        long timestepNanos = (long)(1e9 / UPS);
        long previousTime = System.nanoTime();

        while (!window.shouldClose()) {
            if (System.nanoTime() > previousTime + timestepNanos) {
                long currentTime = System.nanoTime();
                double deltaTime = (currentTime - previousTime) / 1e9;
                window.pollEvents();
                ent1.update(deltaTime);
                renderer.render();
                window.refresh();
                previousTime = currentTime;
            }
        }
        window.terminate();
        cleanup();
    }

    private void cleanup() {

    }
}
