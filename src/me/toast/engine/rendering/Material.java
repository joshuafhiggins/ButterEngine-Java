package me.toast.engine.rendering;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL30.*;

//TODO: Make binding support multiple textures
public class Material {

    //Rest of textures should go here
    public Texture Albedo;

    //TODO: Load from material file
    public Material(String name) {
        try {
            Albedo = TextureLoader.getTexture(".png",
                    new FileInputStream("resources/assets/textures/" + name + "_Albedo.png"),
                    GL_NEAREST);
        } catch (IOException e) {
            System.err.println("Can't find file at: " + "resources/assets/textures/" + name + "_Albedo.png");
        }
    }

    public void Bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, Albedo.getTextureID());
    }

    public void Unbind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void Cleanup() {
        //Rest of textures should go here
        glDeleteTextures(Albedo.getTextureID());
    }
}
