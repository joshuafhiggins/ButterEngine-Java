package me.toast.engine.scene;

import me.toast.engine.rendering.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

    public Mesh Mesh;
    public Vector3f Position, Rotation, Scale;

    Matrix4f model;

    public Entity(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        this.Position = position;
        this.Rotation = rotation;
        this.Scale = scale;
        this.Mesh = mesh;
        this.model = getModelMatrix();
    }

    public void Render(Camera camera) {
        Mesh.Render(this, camera);
    }

    public void Destroy() {
        Mesh.Destroy();
    }

    public Matrix4f getModelMatrix() {
        model = new Matrix4f(); //TODO: Figure out if this a good way of doing this
        model.identity().translate(Position)
                .rotateX((float)Math.toRadians(-Rotation.x))
                .rotateY((float)Math.toRadians(-Rotation.y))
                .rotateZ((float)Math.toRadians(-Rotation.z))
                .scale(Scale);
        return model;
    }
}
