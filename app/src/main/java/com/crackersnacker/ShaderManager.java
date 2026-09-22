package com.crackersnacker;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.crackersnacker.io.ResourceLoader;

public class ShaderManager {
    public enum ShaderId {
        Obj("obj.vert", "tex.frag"),
        Tex("tex.vert", "tex.frag");

        String vertpath;
        String fragpath;

        private ShaderId(String vertpath, String fragpath) {
            this.vertpath = vertpath;
            this.fragpath = fragpath;
        }
    }

    private Map<ShaderId, Shader> shaders;

    public ShaderManager() {
        shaders = new HashMap<ShaderId, Shader>();
    }

    public Shader get(ShaderId id) {
        return shaders.computeIfAbsent(id, this::loadShader);
    }

    private Shader loadShader(ShaderId id) {
        try {
            String fragSrc = ResourceLoader.loadTextResource("shaders/"+id.fragpath);
            String vertSrc = ResourceLoader.loadTextResource("shaders/"+id.vertpath);
            return new Shader(vertSrc, fragSrc);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
