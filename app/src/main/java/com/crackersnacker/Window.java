package com.crackersnacker;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    long window;

    public Window() {
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

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void refresh() {
        glfwSwapBuffers(window);
    }

    public void terminate() {
        glfwTerminate();
    }
}
