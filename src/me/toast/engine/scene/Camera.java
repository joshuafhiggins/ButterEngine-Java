package me.toast.engine.scene;

import me.toast.engine.window.Input;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public Vector3f Position;
    public Vector3f Rotation;

    private final Matrix4f view;
    private final Matrix4f projection;

    private float moveSpeed = 0.05f, mouseSensitivity = 0.005f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    public Camera(Vector3f position, Vector3f rotation) {
        this.Position = position;
        this.Rotation = rotation;

        this.view = new Matrix4f();
        this.projection = new Matrix4f();
    }

    public void Update(Input input) {
        if (!input.GetMouseState()) {
            newMouseX = input.mouseX;
            newMouseY = input.mouseY;
        }

        float x = (float) Math.sin(Math.toRadians(Rotation.y) * moveSpeed);
        float z = (float) Math.cos(Math.toRadians(Rotation.y) * moveSpeed);

        if (input.isKeyDown(GLFW_KEY_A)) Position.add(new Vector3f(-z, 0, x));
        if (input.isKeyDown(GLFW_KEY_D)) Position.add(new Vector3f(z, 0, -x));
        if (input.isKeyDown(GLFW_KEY_W)) Position.add(new Vector3f(-x, 0, -z));
        if (input.isKeyDown(GLFW_KEY_S)) Position.add(new Vector3f(x, 0, z));
        if (input.isKeyDown(GLFW_KEY_SPACE)) Position.add(new Vector3f(0, moveSpeed, 0));
        if (input.isKeyDown(GLFW_KEY_LEFT_SHIFT)) Position.add(new Vector3f(0, -moveSpeed, 0));

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        Rotation.add(new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public void setProjection(float fovy, float aspect, float zNear, float zFar) {
        projection.setPerspective(fovy, aspect, zNear, zFar);
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        view.identity();
        view.rotateXYZ(Rotation);
        view.translate(Position.mul(-1, new Vector3f()));
        return view;
    }
}
