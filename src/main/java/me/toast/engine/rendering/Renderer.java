package me.toast.engine.rendering;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import me.toast.engine.Mod;
import me.toast.engine.world.components.*;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    //TODO: Transparency, sorting
    //TODO: Occlusion Culling
    //TODO: Literally anything we want to render

    private final ImmutableArray<Entity> entities;
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<MeshComponent> rm = ComponentMapper.getFor(MeshComponent.class);

    public Renderer(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, MeshComponent.class).get());

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
    }

    public void Render() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent tComponent = tm.get(entity);
            MeshComponent rComponent = rm.get(entity);

            rComponent.mesh.Render(tComponent, Mod.Cam);
        }
    }

    public void Cleanup() {
        for (Entity entity : entities)
            rm.get(entity).mesh.Cleanup();
    }
}
