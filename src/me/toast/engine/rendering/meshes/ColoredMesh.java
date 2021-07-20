package me.toast.engine.rendering.meshes;

import me.toast.engine.rendering.Vertex;
import me.toast.engine.rendering.BufferObject;

public class ColoredMesh extends Mesh {

    public ColoredMesh(Vertex[] vertices, int[] indices) {
        super(vertices, indices);

        this.bufferObjects = new BufferObject[2];
        Create();
    }

    @Override
    public void Create() {
        VAO = new BufferObject.VAO();
        VAO.Bind();
            bufferObjects[0] = new BufferObject.VBO(vertices, 0);
            bufferObjects[1] = new BufferObject.CBO(vertices, 1);
            IBO = new BufferObject.IBO(indices);
        VAO.Unbind();
    }
}
