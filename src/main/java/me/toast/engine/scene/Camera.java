package me.toast.engine.scene;

import me.toast.engine.Mod;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public Vector3f Position;
    public Vector3f Direction;

    float pitch;
    float yaw = -90f;

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
    float sensitivity = 0.1f;
    float lastX = Mod.Window.Width / 2f, lastY = Mod.Window.Height / 2f;
    boolean firstMouse = true;
    public void Update(float deltaTime) {
        if (Mod.Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_LEFT))
            Mod.Window.InputEvents.SetMouseState(true);
        if (Mod.Window.InputEvents.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT))
            Mod.Window.InputEvents.SetMouseState(false);

        if (!Mod.Window.InputEvents.GetMouseState()) {
            if (firstMouse) {
                lastX = Mod.Window.InputEvents.mouseX;
                lastY = Mod.Window.InputEvents.mouseY;
                firstMouse = false;
            }

            float xoffset = Mod.Window.InputEvents.mouseX - lastX;
            float yoffset = lastY - Mod.Window.InputEvents.mouseY; // reversed since y-coordinates range from bottom to top
            lastX = Mod.Window.InputEvents.mouseX;
            lastY = Mod.Window.InputEvents.mouseY;

            xoffset *= sensitivity;
            yoffset *= sensitivity;

            yaw += xoffset;
            pitch += yoffset;

            if (pitch > 89.0f)
                pitch = 89.0f;
            if (pitch < -89.0f)
                pitch = -89.0f;

            Direction.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            Direction.y = Math.sin(Math.toRadians(pitch));
            Direction.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

            Direction.normalize(cameraFront);
        }

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

        return view;
    }
}
