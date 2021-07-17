package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Shader;
import me.toast.engine.rendering.Vertex;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL30.*;

public class BasicMesh {

    public int VAO, VBO, IBO;

    //Useful for getting information about the arrays //Can't do anything else
    final Vertex[] vertices;
    final int[] indices;

    final int numberOfAttrib;

    public BasicMesh(Vertex[] vertices, int[] indices, int numberOfAttrib) {
        this.vertices = vertices;
        this.indices = indices;
        this.numberOfAttrib = numberOfAttrib;

        Create();
    }
    
    public void Create() {
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

            FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
            positionBuffer.put(Vertex.getPositionData(vertices)).flip();
            VBO = storeData(positionBuffer, 0, 3);

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
            enableVertexAttrib();
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
                    shader.Bind();
                        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
                    shader.Unbind();
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            disableVertexAttrib();
        glBindVertexArray(0);
    }

    private void enableVertexAttrib() {
        for (int i = 0; i < numberOfAttrib - 1; i++) {
            glEnableVertexAttribArray(i);
        }
    }

    private void disableVertexAttrib() {
        for (int i = 0; i < numberOfAttrib - 1; i++) {
            glDisableVertexAttribArray(i);
        }
    }
    
    public void Destroy() {
        glDeleteBuffers(VBO);
        glDeleteBuffers(IBO);
        glDeleteVertexArrays(VAO);
    }
}
