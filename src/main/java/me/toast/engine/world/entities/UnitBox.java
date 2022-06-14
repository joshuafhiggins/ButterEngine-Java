package me.toast.engine.world.entities;

import com.badlogic.ashley.core.Entity;
import me.toast.engine.AssetPool;
import me.toast.engine.Mod;
import me.toast.engine.rendering.Mesh;
import me.toast.engine.rendering.Vertex;
import me.toast.engine.world.components.MeshComponent;
import me.toast.engine.world.components.TransformComponent;
import org.joml.Vector3f;

public class UnitBox extends Entity {

    public MeshComponent meshCom;
    public TransformComponent transform;

    static Vector3f[] cube_vertices = {
            // front
            new Vector3f(-1.0f, -1.0f, 1.0f),
            new Vector3f(1.0f, -1.0f, 1.0f),
            new Vector3f(1.0f, 1.0f, 1.0f),
            new Vector3f(-1.0f, 1.0f, 1.0f),
            // back
            new Vector3f(-1.0f, -1.0f, -1.0f),
            new Vector3f(1.0f, -1.0f, -1.0f),
            new Vector3f(1.0f,  1.0f, -1.0f),
            new Vector3f(-1.0f,  1.0f, -1.0f)
    };

    static int[] cube_elements = {
            // front
            0, 1, 2,
            2, 3, 0,
            // right
            1, 5, 6,
            6, 2, 1,
            // back
            7, 6, 5,
            5, 4, 7,
            // left
            4, 0, 3,
            3, 7, 4,
            // bottom
            4, 5, 1,
            1, 0, 4,
            // top
            3, 2, 6,
            6, 7, 3
    };

    public UnitBox(TransformComponent transform) {
        Vertex[] vertexList = new Vertex[cube_vertices.length];
        for (int j = 0; j < cube_vertices.length; j++) {
            vertexList[j] = new Vertex(cube_vertices[j], new Vector3f(1f, 0f, 0f));
        }

        this.add(transform);
                                                                                                                //Just need a material here, doesn't get used
        this.add(new MeshComponent(new Mesh(vertexList, cube_elements, AssetPool.getShader("Mesh"), AssetPool.getMaterial("dragon"))));

        this.transform = getComponent(TransformComponent.class);
        this.meshCom = this.getComponent(MeshComponent.class);

        Mod.Ashley.addEntity(this);
    }
}
