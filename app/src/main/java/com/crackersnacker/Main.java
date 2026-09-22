package com.crackersnacker;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOError;
import java.io.IOException;

import org.lwjgl.opengl.GL;

import com.crackersnacker.entity.Entity;

public class Main {

	long window;
    ShaderManager shaderManager;

	public void run() {
        shaderManager = new ShaderManager();

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

        Texture tex;
        try {
            tex = new Texture("matt.png");
        } catch (IOException e) {
            throw new IOError(e);
        }

        Shader shad = shaderManager.get(ShaderManager.ShaderId.Obj);
		SpriteRenderer renderer = new SpriteRenderer(tex, shad, 0.8f, 1.0f);
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
            GL.createCapabilities();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			renderer.render();

			glfwSwapBuffers(window);
		}

		glfwTerminate();
	}

	public static void main(String[] args) {
        Entity entity = new Entity();
        BasicScript bs = new BasicScript();
        entity.addScript(bs);
        bs.foo();

        new Main().run();
	}
}
