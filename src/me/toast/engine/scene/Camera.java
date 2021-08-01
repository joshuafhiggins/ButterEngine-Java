package me.toast.engine.scene;

import me.toast.engine.Mod;
import me.toast.engine.window.Input;
import org.joml.Math;
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

    float mainSpeed = 1f; //regular speed
    float shiftAdd = 5f; //multiplied by how long shift is held.  Basically running
    float maxShift = 10f; //Maximum speed when holdin gshift
    float camSens = 0.25f; //How sensitive it with mouse
    private Vector3f lastMouse = new Vector3f(255, 255, 255); //kind of in the middle of the screen, rather than at the top (play)
    private float totalRun= 1.0f;
    float mouseSensitivity = 0.025f;

    public Camera(Vector3f position, Vector3f rotation) {
        this.Position = position;
        this.Rotation = rotation;

        this.view = new Matrix4f();
        this.projection = new Matrix4f();
    }

    public void Update(Input input) {
        if (!input.GetMouseState()) {
            lastMouse = new Vector3f((float) input.mouseY * mouseSensitivity, (float) input.mouseX * mouseSensitivity, 0).sub(lastMouse, new Vector3f());
            lastMouse = new Vector3f(lastMouse.x * camSens, lastMouse.y * camSens, 0);
            lastMouse = new Vector3f(Rotation.x + lastMouse.x, Rotation.y + lastMouse.y, 0);
            Rotation = lastMouse;
            lastMouse = new Vector3f((float) input.mouseY * mouseSensitivity, (float) input.mouseX * mouseSensitivity, 0);
        }
        //Mouse  camera angle done.  

        //Keyboard commands
        float f = 0.0f;
        Vector3f p = GetBaseInput(input);
        if (p.lengthSquared() > 0){ // only move while a direction key is pressed
            if (input.isKeyDown(GLFW_KEY_LEFT_SHIFT)){
                totalRun += Mod.LOADED_MOD.WINDOW.Delta;
                p = p.mul(totalRun, new Vector3f()).mul(shiftAdd, new Vector3f());
                p.x = Math.clamp(-maxShift, maxShift, p.x);
                p.y = Math.clamp(-maxShift, maxShift, p.y);
                p.z = Math.clamp(-maxShift, maxShift, p.z);
            } else {
                totalRun = Math.clamp(1000f, 1f, totalRun * 0.5f);
                p = p.mul(mainSpeed, new Vector3f());
            }

            p = p.mul(Mod.LOADED_MOD.WINDOW.Delta, new Vector3f());
            Vector3f newPosition = Position;
            if (input.isKeyDown(GLFW_KEY_SPACE)){ //If player wants to move on X and Z axis only
                //transform.Translate(p);
                Position = p;
                newPosition.x = Position.x;
                newPosition.z = Position.z;
                Position = newPosition;
            } else {
                //transform.Translate(p);
                Position = p;
            }
        }

        System.out.println("Position: " + Position.x + " " + Position.y + " " + Position.z);
        System.out.println("Rotation: " + Rotation.x + " " + Rotation.y + " " + Rotation.z);
    }

    private Vector3f GetBaseInput(Input input) { //returns the basic values, if it's 0 than it's not active.
        Vector3f p_Velocity = new Vector3f();
        if (input.isKeyDown(GLFW_KEY_W)){
            p_Velocity.add(new Vector3f(0, 0 , 1));
        }
        if (input.isKeyDown(GLFW_KEY_S)){
            p_Velocity.add(new Vector3f(0, 0 , -1));
        }
        if (input.isKeyDown(GLFW_KEY_A)){
            p_Velocity.add(new Vector3f(-1, 0 , 0));
        }
        if (input.isKeyDown(GLFW_KEY_D)){
            p_Velocity.add(new Vector3f(1, 0 , 0));
        }
        return p_Velocity;
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
