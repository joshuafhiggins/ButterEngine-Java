package me.toast.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public long lastTime;

    public void run() {
        System.setProperty("imgui.library.path", "./libs");
        //new DeathmatchMod();

        Reflections reflections = new Reflections("com.mycompany");
        Set<Class<? extends MyInterface>> classes = reflections.getSubTypesOf(MyInterface.class);

        Mod.LOADED_MOD.Init();
        lastTime = System.nanoTime();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Set the clear color

        loop();

        Mod.LOADED_MOD.Window.Cleanup();
    }

    private void loop() {
        // Run the rendering loop until the user has attempted to close
        while ( !glfwWindowShouldClose(Mod.LOADED_MOD.Window.ID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glfwPollEvents(); // Poll for window events. Callbacks will only be invoked during this call.

            Mod.LOADED_MOD.Update();

            Mod.LOADED_MOD.Render();

            Mod.LOADED_MOD.Window.Delta = getDeltaTime();
            glfwSwapBuffers(Mod.LOADED_MOD.Window.ID); // swap the color buffers
        }

        Mod.LOADED_MOD.Cleanup();
    }

    private int getDeltaTime() {
        long currentTime = System.nanoTime();
        int delta = (int)(currentTime - lastTime);
        lastTime = System.nanoTime();
        return delta;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
