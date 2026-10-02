package com.crackersnacker;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Input implements GLFWKeyCallbackI {
    private final Map<String, Digital> digitals;
    private final List<Digital> updatedDigitals;
    private Map<Integer, String> actionMap = new HashMap<>();

    public Input(long window) {
        actionMap = createActionMap();
        digitals = createActions();
        updatedDigitals = new ArrayList<>();

        glfwSetKeyCallback(window, this);
    }

    private Map<String, Digital> createActions() {
        HashMap<String, Digital> actions = new HashMap<>();
        actions.put("Up", new Digital());
        actions.put("Left", new Digital());
        actions.put("Down", new Digital());
        actions.put("Right", new Digital());
        actions.put("Jump", new Digital());
        return actions;
    }

    private Map<Integer, String> createActionMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(GLFW_KEY_W, "Up");
        map.put(GLFW_KEY_A, "Left");
        map.put(GLFW_KEY_S, "Down");
        map.put(GLFW_KEY_D, "Right");

        map.put(GLFW_KEY_UP, "Up");
        map.put(GLFW_KEY_LEFT, "Left");
        map.put(GLFW_KEY_DOWN, "Down");
        map.put(GLFW_KEY_RIGHT, "Right");

        map.put(GLFW_KEY_SPACE, "Jump");
        return map;
    }

    @Override
    public void invoke(long window, int key, int scancode, int event, int mods) {
        if (event == GLFW_PRESS) {
            String action = actionMap.get(key);
            if (action != null) {
                Digital digit = digitals.get(action);
                digit.falling = true;
                digit.down = true;

                if (!updatedDigitals.contains(digit)) {
                    updatedDigitals.add(digit);
                }
            }
        } else if (event == GLFW_RELEASE) {
            String action = actionMap.get(key);
            if (action != null) {
                Digital digit = digitals.get(action);
                digit.rising = true;
                digit.down = false;

                if (!updatedDigitals.contains(digit)) {
                    updatedDigitals.add(digit);
                }
            }
        }
    }

    public void poll() {
        for (Digital digital : updatedDigitals) {
            digital.falling = false;
            digital.rising = false;
        }
        updatedDigitals.clear();
        glfwPollEvents();
    }

    public boolean getKeyDown(String action) {
        return digitals.get(action).down;
    }

    public boolean getKeyFalling(String action) {
        return digitals.get(action).falling;
    }

    public boolean getKeyRising(String action) {
        return digitals.get(action).rising;
    }

    private static class Digital {
        boolean rising = false;
        boolean falling = false;
        boolean down = false;
    }

}
