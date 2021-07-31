package test.game;

import me.toast.engine.Mod;
import me.toast.engine.rendering.*;
import me.toast.engine.scene.Camera;
import me.toast.engine.scene.Entity;
import me.toast.engine.window.Window;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;

public class DeathmatchMod extends Mod {

    public Mesh mesh;
    public Entity test;

    public DeathmatchMod(int width, int height) {
        super("DMTest",
                "Deathmatch Mod",
                "Simple multiplayer deathmatch test in ButterEngine. Use this as an example.",
                "LitlToast",
                width, height);
    }

    @Override
    public void Init() {
        mesh = Model.LoadScene("dragon", ".obj", new Shader("mesh"))[0];
        test = new Entity(new Vector3f(0, -5, -15), new Quaternionf(), new Vector3f(1), mesh);
        camera = new Camera(new Vector3f(), new Vector3f());
        camera.setProjection((float) Math.toRadians(45f), (float) WINDOW.Width / (float) WINDOW.Height, 0.1f, 1000f);

        super.Init();
    }

        //TODO: Make a proper event system
        @Override
        public void Update() {
            if (WINDOW.InputEvents.isKeyDown(GLFW_KEY_ESCAPE))
                glfwSetWindowShouldClose(WINDOW.ID, true);
            if (WINDOW.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
                WINDOW.InputEvents.SetMouseState(true);

            camera.Update(WINDOW.InputEvents);

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
