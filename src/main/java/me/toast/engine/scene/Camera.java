package me.toast.engine.scene;

import me.toast.engine.Mod;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public Vector3f Position;
    public Vector3f Direction;

    Vector3f cameraFront;
    Vector3f cameraUp;

    private final Matrix4f view;
    private final Matrix4f projection;

    public Camera(Vector3f position, Vector3f direction) {
        this.Position = position;
        this.Direction = direction;

        this.cameraFront = new Vector3f(0f, 0f, -1f);
        this.cameraUp = new Vector3f(0f, 1f, 0f);

        this.view = new Matrix4f();
        this.projection = new Matrix4f();
    }

    public void setProjection(float fovy, float aspect, float zNear, float zFar) {
        projection.setPerspective(fovy, aspect, zNear, zFar);
    }

    float cameraSpeed = 2.5f; // adjust accordingly
    public void Update(float deltaTime) {
        if (Mod.Window.InputEvents.isKeyDown(GLFW_KEY_W))
            Position.add(new Vector3f(cameraFront).mul(cameraSpeed * deltaTime));
        if (Mod.Window.InputEvents.isKeyDown(GLFW_KEY_S))
            Position.sub(new Vector3f(cameraFront).mul(cameraSpeed * deltaTime));
        if (Mod.Window.InputEvents.isKeyDown(GLFW_KEY_A))
            Position.sub(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed * deltaTime));
        if (Mod.Window.InputEvents.isKeyDown(GLFW_KEY_D))
            Position.add(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed * deltaTime));
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        view.identity();

        view.lookAt(Position, new Vector3f(Position).add(cameraFront), cameraUp);

        //view.rotate(Rotation);
        //view.translate(Position.mul(-1, new Vector3f()));
        return view;
    }
}
