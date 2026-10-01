package com.crackersnacker;

import com.crackersnacker.entity.Entity;
import com.crackersnacker.entity.EntityManager;
import com.crackersnacker.graphics.*;
import com.crackersnacker.resources.Shaders;
import com.crackersnacker.resources.Textures;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Game {
    Shaders shaders;
    Textures textures;
    EntityManager entityManager;
    Renderer renderer;
    Entity ent1;
    long window;

    private final double UPS;

    public Game(double updatesPerSecond) {
        UPS = updatesPerSecond;
    }

    private void setup() {
        window = Window.create("The Northern Hills", 600, 600, true, true);

        Texture tex = textures.get("matt.png");
        entityManager = new EntityManager();
        renderer = new Renderer();

        Shader shad = shaders.get(Shaders.SPRITE);
        SpriteRenderer spriteRenderer = new SpriteRenderer(tex, shad, renderer, 0.8f, 1.0f);

        ent1 = entityManager
                .buildEntity()
                .withScript(spriteRenderer)
                .withPos(0.5f, 0.5f)
                .getResult();
    }

    private long createWindow() {
        if (!glfwInit()) {
            throw new RuntimeException("GLFW could not be initialized.");
        }

        long window = glfwCreateWindow(600, 600, "GLFW Window", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create GLFW window.");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        glfwMakeContextCurrent(window);
        return window;
    }

    public void loop() {
        setup();
        long timestepNanos = (long)(1e9 / UPS);
        long previousTime = System.nanoTime();

        while (!glfwWindowShouldClose(window)) {
            if (System.nanoTime() > previousTime + timestepNanos) {
                long currentTime = System.nanoTime();
                double deltaTime = (currentTime - previousTime) / 1e9;
                glfwPollEvents();
                ent1.update(deltaTime);
                renderer.render();
                glfwSwapBuffers(window);
                previousTime = currentTime;
            }
        }
        glfwTerminate();
        cleanup();
    }

    private void cleanup() {

    }

}
