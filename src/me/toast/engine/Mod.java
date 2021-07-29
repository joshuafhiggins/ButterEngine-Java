package me.toast.engine;

//TODO: Try to make a distinction between the engine and a mod //TODO: See if IMod is really needed for what we are doing
public class Mod implements IMod {

    public static Mod LOADED_MOD;

    //We'll use this info later in the mod loading
    public String ID;
    public String NAME;
    public String DESCRIPTION;
    public String AUTHOR;

    public Mod(String id, String name, String description, String author) {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.AUTHOR = author;
    }

    @Override
    public void Init() {

    }

        @Override
        public void Input(long window, int key, int scancode, int action, int mods) {

        }

        @Override
        public void WindowResize(long window, int width, int height) {

        }

        @Override
        public void Update() {

        }

        @Override
        public void Render() {

        }

    @Override
    public void Shutdown() {

    }
}
