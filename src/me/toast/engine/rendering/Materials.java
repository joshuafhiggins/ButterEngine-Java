package me.toast.engine.rendering;

public class Materials {

    public static final Materials INSTANCE = new Materials();
    public final Material Dragon;

    public Materials() {
        Dragon = new Material("dragon");
    }

    public void Cleanup() {
        Dragon.Cleanup();
    }
}
