package com.crackersnacker;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    int shaderProgram;

    public Shader(String vertexSrc, String fragmentSrc) {
        int vertex = compileShader(GL_VERTEX_SHADER, vertexSrc);
        int fragment = compileShader(GL_FRAGMENT_SHADER, fragmentSrc);
        
        int program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);
        validateShaderProgram(program);
        shaderProgram = program;

        glDeleteShader(fragment);
        glDeleteShader(vertex);
    }

    public void use() {
        glUseProgram(shaderProgram);
    }

    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);

        glShaderSource(shader, source);
        glCompileShader(shader);

        int[] success = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, success);

        if (success[0] == 0) {
            String infoLog = glGetShaderInfoLog(shader);
            System.out.println("SHADER COMPILATION FAILURE:");
            System.out.println(infoLog);

            glDeleteShader(shader);

            throw new RuntimeException("Shader compilation failed.");
        }

        return shader;
    }

    private void validateShaderProgram(int shaderProgram) {
        int[] success = new int[1];

        glValidateProgram(shaderProgram);

        glGetProgramiv(shaderProgram, GL_VALIDATE_STATUS, success);

        if (success[0] != GL_TRUE) {
            String infoLog = glGetShaderInfoLog(shaderProgram);
            System.out.println("SHADER PROGRAM FAILED TO VALIDATE:");
            System.out.println(infoLog);

            glDeleteProgram(shaderProgram);

            throw new RuntimeException("Shader program validation failed.");
        }
    }
}
