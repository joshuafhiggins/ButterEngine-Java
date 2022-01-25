package me.toast.engine.world.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.bulletphysics.linearmath.Transform;
import me.toast.engine.Mod;
import me.toast.engine.world.components.RenderComponent;
import me.toast.engine.world.components.RigidBodyComponent;
import me.toast.engine.world.components.TransformComponent;
import org.joml.Matrix4f;

import static me.toast.engine.utils.MathConv.ToJOML;

public class RigidBodySystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private final Matrix4f workingMatrixJOML = new Matrix4f();
    private final javax.vecmath.Matrix4f tempMatrixVecmath = new javax.vecmath.Matrix4f();
    private final com.bulletphysics.linearmath.Transform tempTransform = new Transform();

    private final EntityListener listener = new EntityListener() {
        @Override public void entityAdded(Entity entity) {
            Mod.LOADED_MOD.JBullet.AddRigidBody(entity.getComponent(RigidBodyComponent.class).rigidBody);
        }
        @Override public void entityRemoved(Entity entity) {
            if (entity.getComponent(RenderComponent.class) != null && entity.getComponent(RigidBodyComponent.class) != null) {
                entity.getComponent(RenderComponent.class).mesh.Cleanup();
                Mod.LOADED_MOD.JBullet.RemoveRigidBody(entity.getComponent(RigidBodyComponent.class).rigidBody);
            }
        }
    };

    private final ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private final ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    private final ComponentMapper<RigidBodyComponent> rbm = ComponentMapper.getFor(RigidBodyComponent.class);

    public void addedToEngine(Engine engine) {
        engine.addEntityListener(listener);
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, RenderComponent.class, RigidBodyComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent tComponent = tm.get(entity);
            RenderComponent rComponent = rm.get(entity);
            RigidBodyComponent rbComponent = rbm.get(entity);

            ToJOML(rbComponent.rigidBody.getWorldTransform(tempTransform).getMatrix(tempMatrixVecmath), workingMatrixJOML);
            rComponent.mesh.Render(tComponent, Mod.LOADED_MOD.Camera);
        }
    }

    public void removedFromEngine(Engine engine) {
        engine.removeEntityListener(listener);
    }
}
