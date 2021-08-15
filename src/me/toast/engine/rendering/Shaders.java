package me.toast.engine.rendering;

public class Shaders {

    public static final Shaders INSTANCE = new Shaders();
    public final Shader BaseShader;

    public Shaders() {
        BaseShader = new Shader("Mesh");
    }

    public void Destroy() {
        BaseShader.Destroy();
    }
}
