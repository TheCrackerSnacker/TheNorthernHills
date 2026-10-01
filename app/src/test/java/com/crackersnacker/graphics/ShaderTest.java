package com.crackersnacker.graphics;

import com.crackersnacker.resources.ResourceLoader;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShaderTest {

    @BeforeAll
    void setup() {
        Window.createHeadless();
    }

    @Test
    void compilesShader() {
        String vertSrc;
        String fragSrc;
        try {
            vertSrc = ResourceLoader.loadTextResource("shaders/test.vert");
            fragSrc = ResourceLoader.loadTextResource("shaders/test.frag");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> new Shader(vertSrc, fragSrc));
    }

    @Test
    void throwsOnBadShaderSource() {
        String badSrc = "Sphinx of black quartz, judge my vow.";
        assertThrows(ShaderCompilationException.class, () -> new Shader(badSrc, badSrc));
    }

    @AfterAll
    void cleanup() {
        GLFW.glfwTerminate();
    }
}
