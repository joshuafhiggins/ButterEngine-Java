package me.toast.engine;

import com.badlogic.ashley.core.Engine;
import me.toast.engine.physics.Physics;
import me.toast.engine.scene.Camera;
import me.toast.engine.ui.UserInterface;
import me.toast.engine.window.Window;

public abstract class Mod {

    public static Mod LOADED_MOD;

    //We'll use this info later in the mod loading
    public String ID, NAME, DESCRIPTION, AUTHOR;

    public static Window Window;
    public static Engine Ashley;
    public static Physics JBullet;
    public static UserInterface Ultralight;

    public Camera Camera;

    public Mod(String id, String name, String description, String author) {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AUTHOR = author;

        LOADED_MOD = this;
    }

    //TODO: Make an event system

    //The lifecycle of the engine mod
    public void Init() {
        Window = new Window(1280, 720);
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
        AssetPool.CleanupAll();

        M_Cleanup();
    } //Destroy systems, texture, models, assets, etc.

    public abstract void M_Init();
    public abstract void M_Update();
    public abstract void M_Render();
    public abstract void M_Cleanup();
}
