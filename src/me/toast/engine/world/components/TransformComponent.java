package me.toast.engine.world.components;

import com.badlogic.ashley.core.Component;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TransformComponent implements Component {

    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;

    private final Matrix4f model;

    public TransformComponent(Vector3f position, Quaternionf rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        model = new Matrix4f();
    }

    public Matrix4f getModelMatrix() {
        model.identity();
        model.translate(position);
        model.rotate(rotation);
        model.scale(scale);
        return model;
    }
}
