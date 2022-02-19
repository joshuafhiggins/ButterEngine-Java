package me.toast.engine;

import me.toast.dm.DeathmatchMod;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static void main(String[] args) {
        System.setProperty("imgui.library.path", "./libs");

        new DeathmatchMod();

        Mod.LOADED_MOD.Init();
        loop();
        Mod.Window.Cleanup();
    }

    private static void loop() {
        long lastTime = System.nanoTime();

        // Run the rendering loop until the user has attempted to close
        while ( !glfwWindowShouldClose(Mod.Window.ID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwPollEvents(); // Poll for window events. Callbacks will only be invoked during this call.
            Mod.LOADED_MOD.Update();

            Mod.LOADED_MOD.Render();

            lastTime = Mod.Window.Update(lastTime);
        }

        Mod.LOADED_MOD.Cleanup();
    }
}
