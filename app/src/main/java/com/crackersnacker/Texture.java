package com.crackersnacker;

import static org.lwjgl.opengl.GL33.*;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

import org.lwjgl.stb.STBImage;

public class Texture {
    
    int texture;
    int width;
    int height;
    ByteBuffer data;

    public Texture(String filepath) throws IOException {
        String absolutePath;
        try {
            URL resource = Texture.class.getResource("/textures/"+filepath);
            File file = Paths.get(resource.toURI()).toFile();
            absolutePath = file.getAbsolutePath();
            System.out.println(absolutePath);
        } catch (URISyntaxException e) {
            throw new IOError(e);
        }

        int[] widthRef = new int[1];
        int[] heightRef = new int[1];
        int[] numChannelsRef = new int[1];
        data = STBImage.stbi_load(absolutePath, widthRef, heightRef, numChannelsRef, 4);
        width = widthRef[0];
        height = heightRef[0];

        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);
        STBImage.stbi_image_free(data);
    }

    public void use() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
