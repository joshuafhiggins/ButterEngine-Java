package me.toast.engine.rendering;

import me.toast.engine.scene.Camera;
import me.toast.engine.scene.Entity;

import static org.lwjgl.opengl.GL33.*;

public class Mesh {

    public BufferObject.VAO VAO;
    public BufferObject.IBO IBO;
    public BufferObject[] bufferObjects;

    final Shader shader;
    final Material material;

    //Useful for getting information about the arrays //Can't do anything else
    final Vertex[] vertices;
    final int[] indices;

    public Mesh(Vertex[] vertices, int[] indices, Shader shader, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
        this.shader = shader;

        this.bufferObjects = new BufferObject[3];

        Create();
    }

    //Meant to be overridden
    public void Create() {
        VAO = new BufferObject.VAO();
            VAO.Bind();
            bufferObjects[0] = new BufferObject.VBO(vertices, 0);
            bufferObjects[1] = new BufferObject.CBO(vertices, 1);
            bufferObjects[2] = new BufferObject.TBO(vertices, 2);
            IBO = new BufferObject.IBO(indices);
        VAO.Unbind();
    }

    public void SetUniforms(Entity entity, Camera camera) {
        shader.setUniform("model", entity.getModelMatrix());
        shader.setUniform("view", camera.getView());
        shader.setUniform("projection", camera.getProjection());
    }

    //Finally got rendering simplified to one universal method! We'll see how long that lasts
    public void Render(Entity entity, Camera camera) {
        VAO.Bind();
            enableVertexAttrib();
                IBO.Bind();
                    material.Bind();
                        shader.Bind();
                            SetUniforms(entity, camera);
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
        for (int i = 0; i < bufferObjects.length; i++)
            glDisableVertexAttribArray(i);
    }

    public void Destroy() {
        for (BufferObject object : bufferObjects)
            object.Destroy();
        IBO.Destroy();
        VAO.Destroy();

        shader.Destroy();
        material.Destroy();
    }
}
