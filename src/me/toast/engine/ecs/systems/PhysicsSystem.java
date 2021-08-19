package me.toast.engine.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.*;
import com.bulletphysics.linearmath.*;
import me.toast.engine.Mod;
import me.toast.engine.ecs.components.*;
import org.joml.*;

import static me.toast.engine.utils.MathConv.*;

public class PhysicsSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<RigidBodyComponent> rbm = ComponentMapper.getFor(RigidBodyComponent.class);

    //So we don't keep reallocating memory
    private Matrix4f workingMatrixJOML;
    private javax.vecmath.Matrix4f tempMatrixVecmath;
    private Transform tempTransform;

    @Override
    public void addedToEngine(Engine engine) {
        Family family = Family.all(TransformComponent.class, RigidBodyComponent.class).get();
        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, new EntityListener() {
            @Override public void entityAdded(Entity entity) { Mod.LOADED_MOD.JBullet.AddRigidBody(rbm.get(entity).rigidBody); }
            @Override public void entityRemoved(Entity entity) { Mod.LOADED_MOD.JBullet.RemoveRigidBody(rbm.get(entity).rigidBody); }
        });
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            if (!rbm.get(entity).rigidBody.isStaticObject()) {
                workingMatrixJOML.identity();
                tempMatrixVecmath.setIdentity();
                tempTransform.setIdentity();

                ToJOML(
                        rbm.get(entity).rigidBody.getWorldTransform(tempTransform).getMatrix(tempMatrixVecmath),
                        workingMatrixJOML);

                workingMatrixJOML.getTranslation(tm.get(entity).position);
                workingMatrixJOML.getRotation(new AxisAngle4f()).get(tm.get(entity).rotation);
                //Let's just assume for right now that scale isn't changing because of physics
                //workingMatrixJOML.getScale(tm.get(entity).scale);
            }
        }
    }
}
