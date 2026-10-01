package com.crackersnacker.graphics;

import org.lwjgl.opengl.GL;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * A helper class to deal with setting up a window. This is not a full-fledged GLFW API, and it does not intend to be.
 * Instead, the purpose of this class is to ease the setup requirements for tests that call OpenGL functions. Clients
 * should ensure that they clean up the GLFW context after using {@link Window} by calling {@link org.lwjgl.glfw.GLFW#glfwTerminate()}
 * **/
public class Window {
    private static long handle = -1;

    /**
     * If no GLFW window exists, it creates one. If a window does exist, this method does nothing.
     * @return The GLFW handle to the currently existing handle.
     * **/
    public static long create(String title, int width, int height, boolean visible, boolean resizable) {
        if (handle != -1 && glfwInit()) {
            System.err.println("WARNING: Attempted to create duplicate Window, ignored request.");
            System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            return handle;
        }

        if (!glfwInit()) {
            throw new RuntimeException("GLFW could not be initialized.");
        }

        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, visible ? GLFW_TRUE : GLFW_FALSE);

        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create GLFW window.");
        }

        glfwMakeContextCurrent(handle);
        GL.createCapabilities();
        return handle;
    }

    /**
     * Creates a 400px by 400px non-visible, non-resizable window, if no window currently exists.
     * @return The GLFW handle to the currently existing handle.
     * **/
    public static long createHeadless() {
        if (handle != -1) return handle;
        return create("GLFW Headless", 400, 400, false, false);
    }
}
