package com.crackersnacker.resources;

import com.crackersnacker.graphics.Texture;

import java.io.IOError;
import java.io.IOException;

public class Textures extends ResourceManager<String, Texture>{

    @Override
    protected Texture load(String entry) {
        try {
            return new Texture(entry);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
