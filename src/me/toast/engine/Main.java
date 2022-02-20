package me.toast.engine;

import me.toast.dm.DeathmatchMod;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static void main(String[] args) {
        System.setProperty("imgui.library.path", "./libs");

        new DeathmatchMod();

        Mod.Init();
        Mod.LOADED_MOD.M_Init();

        loop();

        Mod.Cleanup();
        Mod.LOADED_MOD.M_Cleanup();
    }

    private static void loop() {
        // Run the rendering loop until the user has attempted to close
        while ( !glfwWindowShouldClose(Mod.Window.ID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            Mod.Render();
            Mod.LOADED_MOD.M_Render();

            Mod.Update();
            Mod.LOADED_MOD.M_Update();

            glfwPollEvents(); // Poll for window events. Callbacks will only be invoked during this call.
        }
    }
}
