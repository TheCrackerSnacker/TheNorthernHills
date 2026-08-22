package com.crackersnacker;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOError;
import java.io.IOException;

import org.lwjgl.opengl.GL;

public class Renderer {
    float[] vertexBuffer = { // x, y, z, r, g, b, s, t
        -0.35f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
        0.35f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        0.35f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
        -0.35f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f
    };
    private int VBO; // Vertex Buffer Object

    final int FLOAT_SIZE = 4;

    private int VAO; // Vertex Array Object

    private int EBO; // Element Buffer Object

    private int[] indices = {
        0, 1, 2,
        0, 2, 3
    };

    private Shader shader;
    private Texture texture;

    public Renderer() {
        GL.createCapabilities();

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * FLOAT_SIZE, 6 * FLOAT_SIZE);
        glEnableVertexAttribArray(2);

        glBindVertexArray(0);

        shader = new Shader("tex.vert", "tex.frag");
        try {
            texture = new Texture("matt.png");
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public void render() {
        shader.use();
        texture.use();
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
