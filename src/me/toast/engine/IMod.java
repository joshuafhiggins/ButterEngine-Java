package me.toast.engine;

public interface IMod {

    //The lifecycle of the engine mod

    void Init(); //Initialize texture, models, assets, etc.
        void Input(long window, int key, int scancode, int action, int mods); //Check for inputs and call their respective action //Gets called before update
        void WindowResize(long window, int width, int height);
        void Update(); //Update the game loop //Do input above
        void Render(); //Call draw calls //Gets called after all updating
    void Shutdown(); //De-initialize texture, models, assets, etc.
}
