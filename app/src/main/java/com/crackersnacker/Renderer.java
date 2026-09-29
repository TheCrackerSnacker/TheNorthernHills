package com.crackersnacker;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private final List<Renderable> elements;

    public Renderer() {
        elements = new ArrayList<>();
    }

    public void render() {
        GL.createCapabilities();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        for (Renderable element : elements) {
            element.render();
        }
    }

    public void registerElement(Renderable element) {
        if (!elements.contains(element)) {
            elements.add(element);
        }
    }

    public void unregisterElement(Renderable element) {
        elements.remove(element);
    }
}
