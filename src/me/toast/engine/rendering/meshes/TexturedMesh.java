package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Vertex;
import me.toast.engine.rendering.BufferObject;

public class TexturedMesh extends Mesh {

    public TexturedMesh(Vertex[] vertices, int[] indices) {
        super(vertices, indices);

        this.bufferObjects = new BufferObject[3];
        Create();
    }

    @Override
    public void Create() {
        VAO = new BufferObject.VAO();
        VAO.Bind();
            bufferObjects[0] = new BufferObject.VBO(vertices, 0);
            bufferObjects[1] = new BufferObject.CBO(vertices, 1);
            bufferObjects[2] = new BufferObject.TBO(vertices, 2);
            IBO = new BufferObject.IBO(indices);
        VAO.Unbind();
    }
}
