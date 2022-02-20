package me.toast.engine.rendering;

import me.toast.engine.AssetPool;

import static org.lwjgl.opengl.GL33.*;

//TODO: Make binding support multiple textures
public class Material {

    public Texture Albedo;

    //TODO: Load from material file
    public Material(String name) {
        Albedo = AssetPool.getTexture(name + "_Albedo");
    }

    public void Bind() {
        glActiveTexture(GL_TEXTURE0);
        Albedo.Bind();
    }

    public void Unbind() {
        glActiveTexture(GL_TEXTURE0);
        Albedo.Unbind();
    }

    public void Cleanup() {
        AssetPool.Cleanup(Albedo);
    }
}
