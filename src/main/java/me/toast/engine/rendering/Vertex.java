package me.toast.engine.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;

//Simple vertex class for position, color, texture coords, normals
public class Vertex {

    public Vector3f position;
    public Vector3f color;
    public Vector2f texCoord;
    public Vector3f normals;

    public Vertex(Vector3f position) {
        this.position = position;
        this.color = new Vector3f(0, 0, 0);
        this.texCoord = new Vector2f(0, 0);
        this.normals = new Vector3f();
    }

    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
        this.texCoord = new Vector2f(0, 0);
        this.normals = new Vector3f();
    }

    public Vertex(Vector3f position, Vector3f color, Vector2f texCoord) {
        this.position = position;
        this.color = color;
        this.texCoord = texCoord;
        this.normals = new Vector3f();
    }

    public Vertex(Vector3f position, Vector3f color, Vector2f texCoord, Vector3f normals) {
        this.position = position;
        this.color = color;
        this.texCoord = texCoord;
        this.normals = normals;
    }
}
