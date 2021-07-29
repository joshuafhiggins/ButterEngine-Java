package test.game;

import me.toast.engine.Mod;
import me.toast.engine.rendering.*;
import me.toast.engine.scene.Camera;
import me.toast.engine.scene.Entity;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class DeathmatchMod extends Mod {

    public Mesh mesh;
    public Entity test;
    public Camera camera;

    public DeathmatchMod(String ID, String name, String description, String author) {
        super(ID, name, description, author);
        Mod.LOADED_MOD = this;
    }

    @Override
    public void Init() {
        mesh = new Mesh(new Vertex[] {
                new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
        }, new int[] {
                0, 1, 2,
                0, 3, 2
        }, new Shader("Mesh"), new Material("Smiley"));
        test = new Entity(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1,1,1), mesh);
        camera = new Camera(new Vector3f(), new Vector3f());

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
            test.Render(camera);

            super.Render();
        }

    @Override
    public void Shutdown() {
        test.Destroy();

        super.Shutdown();
    }
}
