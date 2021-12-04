package me.toast.engine.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import me.toast.engine.Mod;
import me.toast.engine.world.components.*;

public class RenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private final EntityListener listener = new EntityListener() {
        @Override public void entityAdded(Entity entity) {}
        @Override public void entityRemoved(Entity entity) {
            entity.getComponent(RenderComponent.class).mesh.Destroy();
        }
    };

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);

    public void addedToEngine(Engine engine) {
        engine.addEntityListener(listener);
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, RenderComponent.class).exclude(RigidBodyComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent tComponent = tm.get(entity);
            RenderComponent rComponent = rm.get(entity);

            rComponent.mesh.Render(tComponent, Mod.LOADED_MOD.Camera);
        }
    }

    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(listener);
    }
}
