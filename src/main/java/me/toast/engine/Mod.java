package me.toast.engine;

import com.badlogic.ashley.core.Engine;
import me.toast.engine.rendering.Renderer;
import me.toast.engine.scene.Camera;
import me.toast.engine.ui.UserInterface;
import me.toast.engine.window.Window;
import me.toast.engine.world.systems.Systems;

public abstract class Mod {

    public static Mod LOADED_MOD;

    //We'll use this info later in the mod loading
    public String ID, NAME, DESCRIPTION, AUTHOR;

    public static Window Window;
    public static Engine Ashley;
    public static UserInterface UI;
    public static Renderer RenderEngine;
    public static Camera Cam;

    public Mod(String id, String name, String description, String author) {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AUTHOR = author;

        LOADED_MOD = this;
    }

    //TODO: Make an event system

    //The lifecycle of the engine mod
    public static void Init() {
        Window = new Window(1280, 720);
        Ashley = new Engine();
        Systems.AddEntitySystems();
        RenderEngine = new Renderer(Ashley);
        LOADED_MOD.M_Init();
    } //Initialize systems, texture, models, assets, etc.

    public static void Update(float deltaTime) {
        Ashley.update(deltaTime);
        Cam.Update(deltaTime);
        LOADED_MOD.M_Update(deltaTime);
    } //Handle Input and then update game logic

    public static void Render() {
        RenderEngine.Render();
        LOADED_MOD.M_Render();
    } //Draw Stuff

    public static void Cleanup() {
        RenderEngine.Cleanup();
        Ashley.removeAllEntities();
        Ashley.removeAllSystems();
        Window.Cleanup();
        RenderEngine.Cleanup();
        AssetPool.CleanupAll();

        LOADED_MOD.M_Cleanup();
    } //Destroy systems, texture, models, assets, etc.

    public abstract void M_Init();
    public abstract void M_Update(float deltaTime);
    public abstract void M_Render();
    public abstract void M_Cleanup();
}
