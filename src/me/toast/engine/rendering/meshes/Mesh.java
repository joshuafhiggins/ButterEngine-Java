package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Material;
import me.toast.engine.rendering.Shader;
import me.toast.engine.rendering.Vertex;
import me.toast.engine.rendering.BufferObject;

import static org.lwjgl.opengl.GL33.*;

public class Mesh {

    public BufferObject.VAO VAO;
    public BufferObject.IBO IBO;
    public BufferObject[] bufferObjects;

    //Useful for getting information about the arrays //Can't do anything else
    final Vertex[] vertices;
    final int[] indices;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void Create() {}

    //Finally got rendering simplified to one universal method! We'll see how long that lasts
    public void Render(Shader shader, Material material) {
        VAO.Bind();
            enableVertexAttrib();
                IBO.Bind();
                    material.Bind();
                        shader.Bind();
                            DrawElements();
                        shader.Unbind();
                    material.Unbind();
                IBO.Unbind();
            disableVertexAttrib();
        VAO.Unbind();
    }

    //This really isn't needed, just moving OpenGL stuff out of each Mesh class for simplicity
    public void DrawElements() { glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0); }

    public void enableVertexAttrib() {
        for (int i = 0; i < bufferObjects.length; i++) {
            glEnableVertexAttribArray(i);
        }
    }

    public void disableVertexAttrib() {
        for (int i = 0; i < bufferObjects.length; i++) {
            glDisableVertexAttribArray(i);
        }
    }

    public void Destroy() {
        for (BufferObject object : bufferObjects) {
            object.Destroy();
        }
        IBO.Destroy();
        VAO.Destroy();
    }
}
