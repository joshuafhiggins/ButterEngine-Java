package me.toast.engine.util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    public static double timeStarted = Time.getTime();
    public static double deltaTime = 0f;
    private static double endTime, beginTime;

    public static void Init() {
        deltaTime = 0f;
        endTime = 0f;
        beginTime = Time.getTime();
    }

    public static void UpdateDeltaTime() {
        endTime = Time.getTime();
        deltaTime = endTime - beginTime;
        beginTime = Time.getTime();
    }

    public static double getTime() {
        return glfwGetTime();
    }
}
