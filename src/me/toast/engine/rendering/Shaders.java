package me.toast.engine.rendering;

public class Shaders {
    public static Shader BASIC_MESH = new Shader("BASIC_MESH");
    public static Shader COLORED_MESH = new Shader("COLORED_MESH");
    public static Shader TEXTURED_MESH = new Shader("TEXTURED_MESH");

    public static void Destroy() {
        BASIC_MESH.Destroy();
        COLORED_MESH.Destroy();
        TEXTURED_MESH.Destroy();
    }
}
