package me.toast.engine;

import com.badlogic.ashley.core.Engine;
import me.toast.engine.world.scene.Camera;
import me.toast.engine.window.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;

//TODO: Try to make a distinction between the engine and a mod
public class Mod {

    public static Mod LOADED_MOD;

    //We'll use this info later in the mod loading
    public String ID, NAME, DESCRIPTION, AUTHOR;

    //TODO: Naming conventions for mods
    public Window WINDOW;
    public Camera camera;

    public Engine ECS;

    public Mod(String id, String name, String description, String author, int width, int height) {
        LOADED_MOD = this;

        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AUTHOR = author;

        this.WINDOW = new Window(width, height);
        // Make the window visible
        glfwShowWindow(this.WINDOW.ID);
        //Creates our OpenGL Context; TLDR: Very important
        this.WINDOW.Capabilities = GL.createCapabilities();

        ECS = new Engine();
    }

    //TODO: Make decision on whether super statements should be declared for these
    //The lifecycle of the engine mod
    public void Init() {} //Initialize texture, models, assets, etc.
        public void Update() {} //Handle Input and then update game logic
        public void Render() {} //Draw Stuff
    public void Shutdown() {} //De-initialize texture, models, assets, etc.
}
