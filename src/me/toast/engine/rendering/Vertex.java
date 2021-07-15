package me.toast.engine.rendering;

import org.joml.Vector3f;

//Simple vertex class for position, color, texture coords, normals
public class Vertex {

    public Vector3f position;
    public Vector3f color;

    public Vertex(Vector3f position) {
        this.position = position;
        this.color = new Vector3f(0, 0, 0);
    }

    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }
}
