package me.toast.engine;

import com.badlogic.ashley.core.Engine;
import me.toast.engine.physics.Physics;
import me.toast.engine.rendering.Materials;
import me.toast.engine.rendering.Shaders;
import me.toast.engine.scene.Camera;
import me.toast.engine.ui.UserInterface;
import me.toast.engine.window.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;

//TODO: Try to make a distinction between the engine and a mod
public abstract class Mod implements IMod {

    public static Mod LOADED_MOD;

    //We'll use this info later in the mod loading
    public String ID, NAME, DESCRIPTION, AUTHOR;

    //TODO: Naming conventions for mods
    public Window Window;
    public Camera Camera;

    public Engine Ashley;
    public Physics JBullet;
    public UserInterface Ultralight;

    public Mod(String id, String name, String description, String author, int width, int height) {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AUTHOR = author;
        this.Window = new Window(width, height);

        LOADED_MOD = this;
    }

    //TODO: Make a proper event system
    //TODO: Make decision on whether super statements should be declared for these
    //The lifecycle of the engine mod
    public void Init() {
        Ashley = new Engine();
        JBullet = new Physics();
        //Ultralight = new UserInterface(Window);

        M_Init();
    } //Initialize systems, texture, models, assets, etc.

    public void Update() {
        JBullet.Update();
        Ashley.update(Window.Delta);
        //Ultralight.Update();

        M_Update();
    } //Handle Input and then update game logic

    public void Render() {
        //Ultralight.Render();

        M_Render();
    } //Draw Stuff

    public void Cleanup() {
        Ashley.removeAllEntities();
        Ashley.removeAllSystems();
        JBullet.Cleanup();
        //Ultralight.Cleanup();
        Shaders.INSTANCE.Cleanup();
        Materials.INSTANCE.Cleanup();

        M_Cleanup();
    } //De-initialize systems, texture, models, assets, etc.

    @Override public abstract void M_Init();
    @Override public abstract void M_Update();
    @Override public abstract void M_Render();
    @Override public abstract void M_Cleanup();
}
