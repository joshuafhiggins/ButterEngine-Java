package me.toast.dm;

import me.toast.engine.Mod;
import me.toast.engine.rendering.*;
import me.toast.engine.scene.Camera;
import me.toast.engine.world.components.RenderComponent;
import me.toast.engine.world.components.TransformComponent;
import me.toast.engine.world.entities.RenderEntity;
import me.toast.engine.world.systems.RenderSystem;
import me.toast.engine.world.systems.RigidBodySystem;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;

public class DeathmatchMod extends Mod {

    public Mesh mesh;
    public RenderEntity test;

    RenderSystem renderSystem = new RenderSystem();
    RigidBodySystem rigidBodySystem = new RigidBodySystem();

    public DeathmatchMod() {
        super("DMTest",
                "Deathmatch Mod",
                "Simple multiplayer deathmatch test in ButterEngine. Use this as an example.",
                "LitlToast", 1280, 720);
    }

    @Override
    public void M_Init() {
        mesh = Model.LoadScene("dragon.obj", Shaders.INSTANCE.BaseShader, Materials.INSTANCE.Dragon)[0];
        test = new RenderEntity(new TransformComponent(new Vector3f(0, -5, -15), new Quaternionf(), new Vector3f(1)), new RenderComponent(mesh));

        Camera = new Camera(new Vector3f(), new Quaternionf());
        Camera.setProjection((float) Math.toRadians(90f), (float) Window.Width / (float) Window.Height, 0.1f, 1000f);

        Ashley.addSystem(renderSystem);
        Ashley.addSystem(rigidBodySystem);
    }

        @Override
        public void M_Update() {
            if (Window.InputEvents.isKeyDown(GLFW_KEY_ESCAPE))
                glfwSetWindowShouldClose(Window.ID, true);

            if (Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
                Window.InputEvents.SetMouseState(true);
            if (Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT))
                Window.InputEvents.SetMouseState(false);
        }

        @Override
        public void M_Render() {}

    @Override
    public void M_Cleanup() {}
}
