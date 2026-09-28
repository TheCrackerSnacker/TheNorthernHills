package com.crackersnacker;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL;

import com.crackersnacker.ShaderManager;

public class Renderer {
    private final List<Renderable> elements;

    public Renderer() {
        elements = new ArrayList<>();
    }

    public void render() {
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
