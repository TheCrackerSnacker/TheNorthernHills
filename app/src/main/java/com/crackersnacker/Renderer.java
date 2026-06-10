package com.crackersnacker;
import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.opengl.GL;

public class Renderer {
    float[] vertexBuffer = { // x, y, z, r, g b
        -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
        0.5f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
        -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f
    };
    int VBO; // Vertex Buffer Object

    String vsSrc = "#version 330 core\n"
    + "layout (location = 0) in vec3 aPos;\n"
    + "layout (location = 1) in vec3 aColor;\n"
    + "out vec3 vertColor;\n"
    + "void main()\n"
    + "{\n"
    + "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n"
    + "    vertColor = aColor;\n"
    + "}\0";

    int vertexShader;

    String fsSrc = "#version 330 core\n"
    + "out vec4 FragColor;\n"
    + "in vec3 vertColor;\n"
    + "void main()\n"
    + "{\n"
    + "    FragColor = vec4(vertColor, 1.0f);\n"
    + "}\0";

    int fragmentShader;
    int shaderProgram;

    final int FLOAT_SIZE = 4;

    int VAO; // Vertex Array Object

    int EBO; // Element Buffer Object

    int[] indices = {
        0, 1, 2,
        0, 2, 3
    };

    Shader shader;

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

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * FLOAT_SIZE, 3 * FLOAT_SIZE);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);

        shader = new Shader("simple.vs", "simple.fs");
    }

    public void render() {
        shader.use();
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
