package me.toast.engine.rendering;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static me.toast.engine.utils.IOUtils.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    public int ID;

    public Texture(String name) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer components = BufferUtils.createIntBuffer(1);

        ByteBuffer data = null;
        try {
            data = stbi_load_from_memory(ioResourceToByteBuffer("assets/textures/" + name + ".png", 1024), width, height, components, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert data != null;

        ID = glGenTextures();
        Bind();

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (components.get(0) == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                    0, GL_RGB, GL_UNSIGNED_BYTE, data);
        } else if (components.get(0) == 4) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        } else {
            assert false : "Error: (Texture) Unknown number of channels -> '" + components.get(0) + "'";
        }

        //TODO: Set Mipmap Parameters
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(data);
        Unbind();
    }

    public void Bind() {
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    public void Unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void Cleanup() {
        glDeleteTextures(ID);
    }
}
