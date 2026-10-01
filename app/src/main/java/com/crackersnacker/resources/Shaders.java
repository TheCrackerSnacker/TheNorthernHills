package com.crackersnacker.resources;

import com.crackersnacker.graphics.Shader;

import java.io.IOError;
import java.io.IOException;

public class Shaders extends ResourceManager<Shaders.ShaderEntry, Shader> {
    public static final ShaderEntry SPRITE = new ShaderEntry("obj.vert", "tex.frag");

    @Override
    protected Shader load(ShaderEntry entry) {
        try {
            String fragSrc = ResourceLoader.loadTextResource("shaders/"+entry.fragPath);
            String vertSrc = ResourceLoader.loadTextResource("shaders/"+entry.vertPath);
            return new Shader(vertSrc, fragSrc);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public record ShaderEntry(String vertPath, String fragPath) {}
}
