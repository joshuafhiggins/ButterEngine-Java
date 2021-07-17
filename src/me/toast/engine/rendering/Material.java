package me.toast.engine.rendering;

import me.toast.engine.Main;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL30.*;

//TODO: Make binding support multiple textures
public class Material {

    //Rest of textures should go here
    public Texture Albedo;

    public Material(String name) {
        try {
            Albedo = TextureLoader.getTexture("", Main.class.getResourceAsStream("/assets/textures/" + name + "_Albedo.png"), GL_NEAREST);
        } catch (IOException e) {
            System.err.println("Can't find file at: " + Main.class.getResource("/assets/textures/" + name + "_Albedo.png").getFile());
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

    public void Destroy() {
        //Rest of textures should go here
        glDeleteTextures(Albedo.getTextureID());
    }
}
