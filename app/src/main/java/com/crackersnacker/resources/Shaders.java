package com.crackersnacker.resources;

import com.crackersnacker.graphics.Shader;

import java.io.IOError;
import java.io.IOException;

public class Shaders {
    public static final ShaderEntry SPRITE = new ShaderEntry("obj.vert", "tex.frag");

    public record ShaderEntry(String vertPath, String fragPath) implements ResourceManager.Entry<Shader> {

        @Override
        public Shader loadResource() {
            try {
                String fragSrc = ResourceLoader.loadTextResource("shaders/"+fragPath);
                String vertSrc = ResourceLoader.loadTextResource("shaders/"+vertPath);
                return new Shader(vertSrc, fragSrc);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
    }
}
