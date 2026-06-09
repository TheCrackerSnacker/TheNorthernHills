package com.crackersnacker;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL;

public class Renderer {
    float x, y;
    float[] vertexBuffer = { // x, y, z
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.0f,  0.5f, 0.0f
    };
    int VBO; // Vertex Buffer Object

    String vsSrc = "#version 330 core\n"
    + "layout (location = 0) in vec3 aPos;\n"
    + "void main()\n"
    + "{\n"
    + "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n"
    + "}\0";

    int vertexShader;

    String fsSrc = "#version 330 core\n"
    + "out vec4 FragColor;\n"
    + "void main()\n"
    + "{\n"
    + "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
    + "}\0";

    int fragmentShader;
    int shaderProgram;

    final int FLOAT_SIZE = 4;

    int VAO; // Vertex Array Object

    public Renderer() {
        GL.createCapabilities();

        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vsSrc);
        glCompileShader(vertexShader);

        int[] success = new int[1];
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success);

        if (success[0] == 0) {
            String infoLog = glGetShaderInfoLog(vertexShader);
            System.out.println("SHADER COMPILATION FAILURE. ERROR CODE " + success[0] + ":");
            System.out.println(infoLog);
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fsSrc);
        glCompileShader(fragmentShader);

        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, success);

        if (success[0] == 0) {
            String infoLog = glGetShaderInfoLog(fragmentShader);
            System.out.println("SHADER COMPILATION FAILURE. ERROR CODE" + success[0] + ":");
            System.out.println(infoLog);
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        glGetProgramiv(shaderProgram, GL_LINK_STATUS, success);
        if (success[0] == 0) {
            String infoLog = glGetShaderInfoLog(shaderProgram);
            System.out.println("SHADER COMPILATION FAILURE. ERROR CODE" + success[0] + ":");
            System.out.println(infoLog);
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * FLOAT_SIZE, 0);
        glEnableVertexAttribArray(0);
        glUseProgram(shaderProgram);
    }

    public void render() {
        glUseProgram(shaderProgram);
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
