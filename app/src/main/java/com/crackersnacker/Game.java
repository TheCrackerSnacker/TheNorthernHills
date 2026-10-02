package com.crackersnacker;

import com.crackersnacker.entity.Entity;
import com.crackersnacker.entity.EntityManager;
import com.crackersnacker.graphics.*;
import com.crackersnacker.resources.Shaders;
import com.crackersnacker.resources.Textures;

import static org.lwjgl.glfw.GLFW.*;

public class Game {
    Input input;
    Shaders shaders;
    Textures textures;
    EntityManager entityManager;
    Renderer renderer;
    Entity ent1;
    long window;

    private void setup() {
        window = Window.create("The Northern Hills", 600, 600, true, true);
        input = new Input(window);
        shaders = new Shaders();
        textures = new Textures();

        Texture tex = textures.get("matt.png");
        entityManager = new EntityManager();
        renderer = new Renderer();

        Shader shad = shaders.get(Shaders.SPRITE);
        SpriteRenderer spriteRenderer = new SpriteRenderer(tex, shad, renderer, 0.8f, 1.0f);
        PlayerMovement movement = new PlayerMovement(input);

        ent1 = entityManager
                .buildEntity()
                .withScript(spriteRenderer)
                .withScript(movement)
                .withPos(0.5f, 0.5f)
                .getResult();
    }

    public void loop() {
        setup();
        long previousTime = System.nanoTime();

        while (!glfwWindowShouldClose(window)) {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - previousTime) / 1e9;
            input.poll();
            ent1.update(deltaTime);
            renderer.render();
            glfwSwapBuffers(window);
            previousTime = currentTime;
        }
        glfwTerminate();
    }
}
