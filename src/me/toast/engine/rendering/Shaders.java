package me.toast.engine.rendering;

public class Shaders {
    public static Shader BASIC_RENDERING = new Shader("BASIC_RENDERING");
    public static Shader COLORED_MESH = new Shader("COLORED_MESH");

    public static void Destroy() {
        BASIC_RENDERING.Destroy();
        COLORED_MESH.Destroy();
    }
}
