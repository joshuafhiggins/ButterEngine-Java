package test.game;

import me.toast.engine.Mod;
import me.toast.engine.rendering.*;
import me.toast.engine.scene.Camera;
import me.toast.engine.ecs.entities.RenderEntity;
import me.toast.engine.ecs.systems.RenderSystem;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;

public class DeathmatchMod extends Mod {

    public Mesh mesh;
    public RenderEntity test;

    public DeathmatchMod(int width, int height) {
        super("DMTest",
                "Deathmatch Mod",
                "Simple multiplayer deathmatch test in ButterEngine. Use this as an example.",
                "LitlToast",
                width, height);
    }

    @Override
    public void Init() {
        mesh = Model.LoadScene("dragon.obj", Shaders.INSTANCE.BaseShader, Materials.INSTANCE.Dragon)[0];
        Ashley.addSystem(new RenderSystem());
        test = new RenderEntity(new Vector3f(0, -5, -15), new Quaternionf(), new Vector3f(1), mesh);
        Ashley.addEntity(test);
        Camera = new Camera(new Vector3f(), new Quaternionf());
        Camera.setProjection((float) Math.toRadians(45f), (float) Window.Width / (float) Window.Height, 0.1f, 1000f);

        super.Init();
    }

        //TODO: Make a proper event system
        @Override
        public void Update() {
            if (Window.InputEvents.isKeyDown(GLFW_KEY_ESCAPE))
                glfwSetWindowShouldClose(Window.ID, true);

            if (Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
                Window.InputEvents.SetMouseState(true);
            if (Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT))
                Window.InputEvents.SetMouseState(false);

            Mod.LOADED_MOD.Ashley.update(Mod.LOADED_MOD.Window.Delta);

            super.Update();
        }

        @Override
        public void Render() {
            super.Render();
        }

    @Override
    public void Shutdown() {
        super.Shutdown();
    }
}
