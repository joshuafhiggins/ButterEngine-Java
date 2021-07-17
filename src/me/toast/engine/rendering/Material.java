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

    public Material(String path) {
        try {
            Albedo = TextureLoader.getTexture(path.split("[.]")[1], Main.class.getResourceAsStream(path), GL_NEAREST);
        } catch (IOException e) {
            System.err.println("Can't find texture at " + path);
        }
    }

    public void Bind() {
        
    }

    public void Destroy() {
        //Rest of textures should go here
        glDeleteTextures(Albedo.getTextureID());
    }
}
