package test.game;

import me.toast.engine.Main;
import me.toast.engine.Mod;
import me.toast.engine.rendering.*;
import me.toast.engine.scene.Camera;
import me.toast.engine.scene.Entity;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

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
        mesh = Model.LoadScene("dragon", ".obj", new Shader("mesh"))[0];
        test = new Entity(new Vector3f(0, -5, -15), new Quaternionf(), new Vector3f(1), mesh);
        camera = new Camera(new Vector3f(), new Quaternionf());
        camera.setProjection((float) Math.toRadians(45f), (float) Main.WIDTH / (float) Main.HEIGHT, 0.1f, 1000f);

        super.Init();
    }

        //TODO: Inputs and now Window Resizes should be made into a separate area as Events
        @Override
        public void Input(long window, int key, int scancode, int action, int mods) {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);

            super.Input(window, key, scancode, action, mods);
        }

        @Override
        public void WindowResize(long window, int width, int height) {
            if(Main.CAPABILITIES != null) {
                Main.WIDTH = width;
                Main.HEIGHT = height;
                glViewport(0, 0, width, height);
                camera.setProjection((float) Math.toRadians(45f), (float) width/ (float) height, 0.1f, 1000f);
            }
            super.WindowResize(window, width, height);
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
