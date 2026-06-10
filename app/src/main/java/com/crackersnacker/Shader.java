package com.crackersnacker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    int shaderProgram;

    public Shader(String vertexFilepath, String fragmentFilepath) {
        int vertex = createShader(GL_VERTEX_SHADER, vertexFilepath);
        int fragment = createShader(GL_FRAGMENT_SHADER, fragmentFilepath);
        
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

    private int createShader(int type, String filepath) {
        String src = "";
        try {
            src = loadShaderSource(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Failed to load shader at "+filepath);
        }
        return compileShader(type, src);
    }

    private String loadShaderSource(String filepath) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("shaders/" + filepath);
        return readFromInputStream(is);
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int[] success = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, success);

        if (success[0] == 0) {
            String infoLog = glGetShaderInfoLog(shader);
            System.out.println("SHADER COMPILATION FAILURE. ERROR CODE " + success[0] + ":");
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
