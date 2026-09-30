package com.crackersnacker;

import com.crackersnacker.entity.Entity;
import com.crackersnacker.entity.EntityManager;
import com.crackersnacker.graphics.Renderer;
import com.crackersnacker.graphics.Shader;
import com.crackersnacker.graphics.SpriteRenderer;
import com.crackersnacker.graphics.Texture;
import com.crackersnacker.resources.ResourceManager;
import com.crackersnacker.resources.Shaders;

import java.io.IOError;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Game {
    EntityManager entityManager;
    Renderer renderer;
    Entity ent1;
    long window;

    private final double UPS;

    public Game(double updatesPerSecond) {
        UPS = updatesPerSecond;
    }

    private void setup() {
        createWindow();

        Texture tex;
        try {
            tex = new Texture("matt.png");
        } catch (IOException e) {
            throw new IOError(e);
        }

        ResourceManager resources = new ResourceManager();

        entityManager = new EntityManager();
        renderer = new Renderer();

        Shader shad = resources.get(Shaders.SPRITE);
        SpriteRenderer spriteRenderer = new SpriteRenderer(tex, shad, renderer, 0.8f, 1.0f);

        ent1 = entityManager
                .buildEntity()
                .withScript(spriteRenderer)
                .withPos(0.5f, 0.5f)
                .getResult();
    }

    private void createWindow() {
        if (!glfwInit()) {
            System.err.println("Error: GLFW could not be initialized!");
            return;
        }

        window = glfwCreateWindow(600, 600, "GLFW Window", NULL, NULL);
        if (window == NULL) {
            System.err.println("Failed to create GLFW window.");
            glfwTerminate();
            return;
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        glfwMakeContextCurrent(window);
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
