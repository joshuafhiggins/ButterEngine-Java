package me.toast.engine.rendering;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import me.toast.engine.Mod;
import me.toast.engine.world.components.*;

public class Renderer {

    //TODO: Transparency, sorting
    //TODO: Occlusion Culling
    //TODO: Literally anything we want to render

    private final ImmutableArray<Entity> entities;
    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<MeshComponent> rm = ComponentMapper.getFor(MeshComponent.class);

    public Renderer(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, MeshComponent.class).get());
    }

    public void Render() {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent tComponent = tm.get(entity);
            MeshComponent rComponent = rm.get(entity);

            rComponent.mesh.Render(tComponent, Mod.LOADED_MOD.Camera);
        }
    }

    public void Cleanup() {
        for (Entity entity : entities)
            rm.get(entity).mesh.Cleanup();
    }
}
