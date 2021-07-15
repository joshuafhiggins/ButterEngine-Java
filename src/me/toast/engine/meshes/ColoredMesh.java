package me.toast.engine.meshes;

import me.toast.engine.rendering.Shader;
import me.toast.engine.rendering.Vertex;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL30.*;

public class ColoredMesh {

    public int VAO, VBO, IBO, CBO;

    //Useful for getting information about the arrays //Can't do anything else
    final Vertex[] vertices;
    final int[] indices;

    public ColoredMesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;

        Create();
    }

    public void Create() {
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

            FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
            float[] positionData = new float[vertices.length * 3];
            for (int i = 0; i < vertices.length; i++) {
                positionData[i * 3] = vertices[i].position.x;
                positionData[i * 3 + 1] = vertices[i].position.y;
                positionData[i * 3 + 2] = vertices[i].position.z;
            }
            positionBuffer.put(positionData).flip();
            VBO = storeData(positionBuffer, 0, 3);

            FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
            float[] colorData = new float[vertices.length * 3];
            for (int i = 0; i < vertices.length; i++) {
                colorData[i * 3] = vertices[i].color.x();
                colorData[i * 3 + 1] = vertices[i].color.y();
                colorData[i * 3 + 2] = vertices[i].color.z();
            }
            colorBuffer.put(colorData).flip();
            CBO = storeData(colorBuffer, 1, 3);

            IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            IBO = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void Render(Shader shader) {
        glBindVertexArray(VAO);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
                    shader.Bind();
                        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
                    shader.Unbind();
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void Destroy() {
        glDeleteBuffers(VBO);
        glDeleteBuffers(IBO);
        glDeleteVertexArrays(VAO);
    }
}
