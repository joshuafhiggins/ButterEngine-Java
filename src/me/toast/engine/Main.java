package me.toast.engine;

import me.toast.engine.rendering.Materials;
import me.toast.engine.rendering.Shaders;
import test.game.DeathmatchMod;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public void run() {
        System.setProperty("imgui.library.path", "./libs");
        new DeathmatchMod(1280, 720);

        Mod.LOADED_MOD.Init();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Set the clear color

        loop();

        Mod.LOADED_MOD.Window.Cleanup();
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        while ( !glfwWindowShouldClose(Mod.LOADED_MOD.Window.ID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            Mod.LOADED_MOD.Update();

            Mod.LOADED_MOD.Render();

            Mod.LOADED_MOD.Window.Update();
        }

        Mod.LOADED_MOD.Cleanup();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
