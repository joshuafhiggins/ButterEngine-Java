package me.toast.engine.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import me.toast.engine.Mod;
import me.toast.engine.ecs.components.*;

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        Family family = Family.all(TransformComponent.class, RenderComponent.class).get();
        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, new EntityListener() {
            @Override public void entityAdded(Entity entity) { /* Do Nothing */ }
            @Override public void entityRemoved(Entity entity) { rm.get(entity).mesh.Destroy(); }
        });
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities)
            rm.get(entity).mesh.Render(tm.get(entity), Mod.LOADED_MOD.Camera);
    }
}
