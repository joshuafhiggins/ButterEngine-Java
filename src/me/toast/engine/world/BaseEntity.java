package me.toast.engine.world;

import com.badlogic.ashley.core.Entity;
import me.toast.engine.rendering.Mesh;
import me.toast.engine.world.scene.Camera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BaseEntity extends Entity {

    //TODO (LEFT OFF):
    // Replace Objects in here with Components
    // Rename from BaseEntity to reflect use
    // Research ImmutableArray
    // Make a BaseEntity system
    // Move Rendering to System
    
    public Mesh Mesh;
    public Vector3f Position;
    public Quaternionf Rotation;
    public Vector3f Scale;

    private final Matrix4f model;

    public BaseEntity(Vector3f position, Quaternionf rotation, Vector3f scale, Mesh mesh) {
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
