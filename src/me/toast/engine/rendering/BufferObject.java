package me.toast.engine.rendering;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class BufferObject {

    public int ID;

    public static class VAO extends BufferObject {
        public VAO() { ID = glGenVertexArrays(); }
        @Override public void Bind() { glBindVertexArray(ID); }
        @Override public void Unbind() { glBindVertexArray(0); }
        @Override public void Destroy() { glDeleteVertexArrays(ID); }
    }

    public static class IBO extends BufferObject {
        public IBO(int[] indices) { ID = storeData(MemoryUtil.memAllocInt(indices.length).put(indices).flip()); }
        @Override public void Bind() { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ID); }
        @Override public void Unbind() { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); }
        @Override public void Destroy() { glDeleteBuffers(ID); }
    }

    public static class VBO extends BufferObject {
        public VBO(Vertex[] vertices, int index) { ID = storeData(MemoryUtil.memAllocFloat(vertices.length * 3).put(getPositionData(vertices)).flip(), index, 3); }
        @Override public void Bind() { glBindBuffer(GL_ARRAY_BUFFER, ID); }
        @Override public void Unbind() { glBindBuffer(GL_ARRAY_BUFFER, 0); }
        @Override public void Destroy() { glDeleteBuffers(ID); }
    }

    public static class CBO extends BufferObject {
        public CBO(Vertex[] vertices, int index) { ID = storeData(MemoryUtil.memAllocFloat(vertices.length * 3).put(getColorData(vertices)).flip(), index, 3); }
        @Override public void Bind() { glBindBuffer(GL_ARRAY_BUFFER, ID); }
        @Override public void Unbind() { glBindBuffer(GL_ARRAY_BUFFER, 0); }
        @Override public void Destroy() { glDeleteBuffers(ID); }
    }

    public static class TBO extends BufferObject {
        public TBO(Vertex[] vertices, int index) { ID = storeData(MemoryUtil.memAllocFloat(vertices.length * 2).put(getTextureCoordData(vertices)).flip(), index, 2); }
        @Override public void Bind() { glBindBuffer(GL_ARRAY_BUFFER, ID); }
        @Override public void Unbind() { glBindBuffer(GL_ARRAY_BUFFER, 0); }
        @Override public void Destroy() { glDeleteBuffers(ID); }
    }

    //Use for like anything else
    public int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    //Use for IBOs
    public int storeData(IntBuffer indicesBuffer) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        return bufferID;
    }

    //For overrides
    public void Destroy() {}
    public void Bind() {}
    public void Unbind() {}

    public float[] getPositionData(Vertex[] vertices) {
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3] = vertices[i].position.x;
            positionData[i * 3 + 1] = vertices[i].position.y;
            positionData[i * 3 + 2] = vertices[i].position.z;
        }
        return positionData;
    }

    public float[] getColorData(Vertex[] vertices) {
        float[] colorData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i * 3] = vertices[i].color.x;
            colorData[i * 3 + 1] = vertices[i].color.y;
            colorData[i * 3 + 2] = vertices[i].color.z;
        }
        return colorData;
    }

    public float[] getTextureCoordData(Vertex[] vertices) {
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2] = vertices[i].texCoord.x;
            textureData[i * 2 + 1] = vertices[i].texCoord.y;
        }
        return textureData;
    }
}