package com.crackersnacker;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
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

import com.crackersnacker.entity.Script;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class SpriteRenderer extends Script implements Renderable {

    private float rotation;
    private Texture tex;
    private Shader shader;
    private final Matrix4f modelMat;
    private final FloatBuffer matBuf;
    private final Renderer renderer;

    private int VAO; // Vertex Array Object

    private float width;
    private float height;

    private final int[] indices = {
        0, 1, 2,
        0, 2, 3
    };
    
    public SpriteRenderer(Texture tex, Shader shader, Renderer renderer, float width, float height) {
        matBuf = BufferUtils.createFloatBuffer(16);
        modelMat = new Matrix4f();
        this.tex = tex;
        this.shader = shader;
        this.width = width;
        this.height = height;
        this.renderer = renderer;
        rotation = 0;// (float) (Math.PI / 2);
    }

    @Override
    public void init() {
        setupRenderer();
        renderer.registerElement(this);
    }

    private void setupRenderer() {
        GL.createCapabilities();

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, createRectVertices(), GL_STATIC_DRAW);

        int EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        int FLOAT_SIZE = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * FLOAT_SIZE, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    private float[] createRectVertices() {
        return new float[]{ // x, y, z, r, g, b, s, t
            -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
            0.5f,  -0.5f, 0.0f, 1.0f, 1.0f,
            0.5f,  0.5f,  0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f,  0.0f, 0.0f, 0.0f
        };
    }

    @Override
    public void onEnable() {
        renderer.registerElement(this);
    }

    @Override
    public void onDisable() {
        renderer.unregisterElement(this);
    }

    public void render() {
        modelMat.translation(getEntity().getX(), getEntity().getY(), 0).rotate(rotation, 0, 0, 1).scale(width, height, 0).get(matBuf);

        glUniformMatrix4fv(0, false, matBuf);
        shader.use();
        tex.use();
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotationRads) {
        this.rotation = rotationRads;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}