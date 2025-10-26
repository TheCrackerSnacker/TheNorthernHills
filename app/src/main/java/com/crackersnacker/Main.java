package com.crackersnacker;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL46.*;

public class Main {

	long window;

	public void run() {
		if (!glfwInit()) {
			System.err.println("Error: GLFW could not be initialized!");
			return;
		}

		window = glfwCreateWindow(800, 600, "GLFW Window", NULL, NULL);
		if (window == NULL) {
			System.err.println("Failed to create GLFW window.");
			glfwTerminate();
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

		GLFW.glfwMakeContextCurrent(window);
		Renderer renderer = new Renderer();
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			renderer.render();

			glfwSwapBuffers(window);
		}

		glfwTerminate();
	}

	public static void main(String[] args) {
		new Main().run();
	}

}