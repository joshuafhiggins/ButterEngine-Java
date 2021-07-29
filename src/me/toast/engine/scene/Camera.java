package me.toast.engine.scene;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {

    public Vector3f Position;
    public Quaternionf Rotation;

    private final Matrix4f view;
    private final Matrix4f projection;

    public Camera(Vector3f position, Quaternionf rotation) {
        this.Position = position;
        this.Rotation = rotation;

        this.view = new Matrix4f();
        this.projection = new Matrix4f();
    }

    public void setProjection(float fovy, float aspect, float zNear, float zFar) {
        projection.setPerspective(fovy, aspect, zNear, zFar);
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        view.identity();
        view.rotate(Rotation.conjugate(new Quaternionf()));
        view.translate(Position.mul(-1, new Vector3f()));
        return view;
    }
}
