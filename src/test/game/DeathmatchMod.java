package test.game;

import me.toast.engine.Mod;
import me.toast.engine.rendering.Material;
import me.toast.engine.rendering.meshes.ColoredMesh;
import me.toast.engine.rendering.Shaders;
import me.toast.engine.rendering.Vertex;
import me.toast.engine.rendering.meshes.TexturedMesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class DeathmatchMod extends Mod {

    public TexturedMesh mesh;
    public Material material;

    public DeathmatchMod(String ID, String name, String description, String author) {
        super(ID, name, description, author);
        Mod.LOADED_MOD = this;
    }

    @Override
    public void Init() {
        mesh = new TexturedMesh(new Vertex[] {
                new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
        }, new int[] {
                0, 1, 2,
                0, 3, 2
        }, 2);

        material = new Material("dirt");

        super.Init();
    }

        @Override
        public void Input(long window, int key, int scancode, int action, int mods) {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);

            super.Input(window, key, scancode, action, mods);
        }

        @Override
        public void Update() {
            super.Update();
        }

        @Override
        public void Render() {
            mesh.Render(Shaders.TEXTURED_MESH, material);

            super.Render();
        }

    @Override
    public void Shutdown() {
        Shaders.Destroy();
        mesh.Destroy();
        material.Destroy();

        super.Shutdown();
    }
}
