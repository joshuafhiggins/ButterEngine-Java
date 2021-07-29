package me.toast.engine.scene;

import me.toast.engine.rendering.Mesh;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {

    public Mesh Mesh;
    public Vector3f Position;
    public Quaternionf Rotation;
    public Vector3f Scale;

    private final Matrix4f model;

    public Entity(Vector3f position, Quaternionf rotation, Vector3f scale, Mesh mesh) {
        this.Position = position;
        this.Rotation = rotation;
        this.Scale = scale;
        this.Mesh = mesh;

        this.model = new Matrix4f();
    }

    public void Render(Camera camera) {
        Mesh.Render(this, camera);
    }

    public void Destroy() {
        Mesh.Destroy();
    }

    public Matrix4f getModelMatrix() {
        model.identity();
        model.translate(Position);
        model.rotate(Rotation);
        model.scale(Scale);
        return model;
    }
}
