package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Vertex;
import me.toast.engine.rendering.BufferObject;

public class BasicMesh extends Mesh {

    public BasicMesh(Vertex[] vertices, int[] indices) {
        super(vertices, indices);

        this.bufferObjects = new BufferObject[1];
        Create();
    }

    @Override
    public void Create() {
        VAO = new BufferObject.VAO();
        VAO.Bind();
            bufferObjects[0] = new BufferObject.VBO(vertices, 0);
            IBO = new BufferObject.IBO(indices);
        VAO.Unbind();
    }
}
