package com.crackersnacker;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;;

public class Renderer {
    float x, y;
    int vertexArrayId; // The ID of our vertex array object (VAO)
    float[] vertexBuffer = { // x, y, z
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.0f,  0.5f, 0.0f
    };
    int vertexBufferId;

    public Renderer() {
		GL.createCapabilities();

        vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);
        vertexBufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
        System.out.println(glGetError());
    }

    public void render() {
        changePos(0.01f, 0.01f);
        glClear(GL_COLOR_BUFFER_BIT);
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glDisableVertexAttribArray(0);
    }

    public void changePos(float dx, float dy) {
        for (int i = 0; i < 9; i += 3) {
            vertexBuffer[i] += dx;
        }

        for (int i = 1; i < 9; i += 3) {
            vertexBuffer[i] += dy;
        }
    }
}
