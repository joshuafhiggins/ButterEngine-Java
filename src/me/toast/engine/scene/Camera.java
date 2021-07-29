package me.toast.engine.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    public Vector3f Up = new Vector3f(0, 1, 0);
    public Vector3f Position, Rotation;

    Matrix4f view;
    public final Matrix4f Projection;

    public Camera(Vector3f position, Vector3f rotation) {
        this.Position = position;
        this.Rotation = rotation;

        view = getViewMatrix();
        Projection = new Matrix4f().perspective((float) Math.toRadians(45f), 300f/300f, 0.1f, 100f);
    }

    public Matrix4f getViewMatrix() {
        view = new Matrix4f();
        view.identity();
        // First do the rotation so camera rotates over its position
        /*
        view
                .rotate((float)Math.toRadians(Rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(Rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(Rotation.z), new Vector3f(0, 0, 1));*/

        view.lookAt(Position, Rotation, new Vector3f(0, 1, 0));
        // Then do the translation
        view.translate(-Position.x, -Position.y, -Position.z);
        return view;
    }
}
