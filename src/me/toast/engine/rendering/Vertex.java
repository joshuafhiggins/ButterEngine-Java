package me.toast.engine.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;

//Simple vertex class for position, color, texture coords, normals
public class Vertex {

    public Vector3f position;
    public Vector3f color;
    public Vector2f texCoord;

    public Vertex(Vector3f position) {
        this.position = position;
        this.color = new Vector3f(0, 0, 0);
        this.texCoord = new Vector2f(0, 0);
    }

    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
        this.texCoord = new Vector2f(0, 0);
    }

    public Vertex(Vector3f position, Vector3f color, Vector2f texCoord) {
        this.position = position;
        this.color = color;
        this.texCoord = texCoord;
    }

    public static float[] getPositionData(Vertex[] vertices) {
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3] = vertices[i].position.x;
            positionData[i * 3 + 1] = vertices[i].position.y;
            positionData[i * 3 + 2] = vertices[i].position.z;
        }
        return positionData;
    }

    public static float[] getColorData(Vertex[] vertices) {
        float[] colorData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i * 3] = vertices[i].color.x;
            colorData[i * 3 + 1] = vertices[i].color.y;
            colorData[i * 3 + 2] = vertices[i].color.z;
        }
        return colorData;
    }

    public static float[] getTextureCoordData(Vertex[] vertices) {
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2] = vertices[i].texCoord.x;
            textureData[i * 2 + 1] = vertices[i].texCoord.y;
        }
        return textureData;
    }
}
