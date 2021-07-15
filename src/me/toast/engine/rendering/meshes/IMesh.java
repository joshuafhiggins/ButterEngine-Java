package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Shader;

//Meshes should be made with these methods //TODO: Find a way to design meshes to be structured like Shaders //TODO: Actually use this
public interface IMesh {
    void Create();
    void Render(Shader shader);
    void Destroy();
}
