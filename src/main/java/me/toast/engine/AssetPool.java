package me.toast.engine;

import me.toast.engine.rendering.*;

import java.io.File;
import java.util.*;

public class AssetPool {

    //TODO: Make a system for unloading no longer used resources

    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, Material> materials = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File("/assets/shaders/" + resourceName + ".vert");
        if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
            return AssetPool.shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File("assets/textures/" + resourceName + ".png");
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static Material getMaterial(String resourceName) {
        if (AssetPool.materials.containsKey(resourceName)) {
            return AssetPool.materials.get(resourceName);
        } else {
            Material material = new Material(resourceName);
            AssetPool.materials.put(resourceName, material);
            return material;
        }
    }

    public static void Cleanup(Shader shader) {
        AssetPool.shaders.values().remove(shader);
        shader.Cleanup();
    }
    public static void Cleanup(Texture texture) {
        AssetPool.textures.values().remove(texture);
        texture.Cleanup();
    }
    public static void Cleanup(Material material) {
        AssetPool.materials.values().remove(material);
        material.Cleanup();
    }

    public static void CleanupAll() {
        for (Shader shader : shaders.values()) {
            shader.Cleanup();
        }
        shaders.clear();
        for (Texture texture : textures.values()) {
            texture.Cleanup();
        }
        textures.clear();
        for (Material material : materials.values()) {
            material.Cleanup();
        }
        materials.clear();
    }
}
