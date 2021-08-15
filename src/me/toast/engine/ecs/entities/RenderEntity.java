package me.toast.engine.ecs.entities;

import com.badlogic.ashley.core.Entity;
import me.toast.engine.rendering.Mesh;
import me.toast.engine.ecs.components.RenderComponent;
import me.toast.engine.ecs.components.TransformComponent;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RenderEntity extends Entity {

    //TODO Research ImmutableArray

    public TransformComponent transformComponent;
    public RenderComponent renderComponent;

    public RenderEntity(Vector3f position, Quaternionf rotation, Vector3f scale, Mesh mesh) {
        super();
        transformComponent = addAndReturn(new TransformComponent(position, rotation, scale));
        renderComponent = addAndReturn(new RenderComponent(mesh));
    }
}
