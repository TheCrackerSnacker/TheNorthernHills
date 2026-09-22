package com.crackersnacker;

import static org.lwjgl.opengl.GL33.GL_FLOAT;
import static org.lwjgl.opengl.GL33.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL33.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL33.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL33.GL_TRIANGLES;
import static org.lwjgl.opengl.GL33.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL33.glBindBuffer;
import static org.lwjgl.opengl.GL33.glBufferData;
import static org.lwjgl.opengl.GL33.glDrawElements;
import static org.lwjgl.opengl.GL33.glGenBuffers;
import static org.lwjgl.opengl.GL33.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL33.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL33.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class SpriteRenderer {

    private float x, y;
    private float rotation;
    private Texture tex;
    private FloatBuffer matBuf;
    private FloatBuffer viewMatBuf;
    private FloatBuffer projMatBuf;
    private Shader shader;

    private int VBO; // Vertex Buffer Object
    private final int FLOAT_SIZE = 4;
    private int VAO; // Vertex Array Object
    private int EBO; // Element Buffer Object

    private final int[] indices = {
        0, 1, 2,
        0, 2, 3
    };
    
    public SpriteRenderer(Texture tex, Shader shader, float width, float height) {
        matBuf = BufferUtils.createFloatBuffer(16);
        viewMatBuf = BufferUtils.createFloatBuffer(16);
        projMatBuf = BufferUtils.createFloatBuffer(16);
        this.tex = tex;
        this.shader = shader;
        setupRenderer(width, height);
        x = 0.5f;
        y = 0.5f;
        rotation = 0;// (float) (Math.PI / 2);
    }

    private void setupRenderer(float width, float height) {
        GL.createCapabilities();

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, createRectVertices(width, height), GL_STATIC_DRAW);

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
    }

    private float[] createRectVertices(float width, float height) {
        float[] vertexBuffer = { // x, y, z, r, g, b, s, t
            -width/2f, -height/2f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            width/2f,  -height/2f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            width/2f,  height/2f,  0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            -width/2f, height/2f,  0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f
        };
        return vertexBuffer;
    }

    public void render() {
        rotation += 0.001;
        new Matrix4f().translate(x, y, 0).rotate(rotation, 0, 0, 1).get(matBuf);

        glUniformMatrix4fv(0, false, matBuf);
        shader.use();
        tex.use();
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
