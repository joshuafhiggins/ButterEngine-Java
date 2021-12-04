package me.toast.engine.world.entities;

import me.toast.engine.world.components.*;

public class PhysicsEntity extends RenderEntity {

    public RigidBodyComponent rigidBodyCom;

    public PhysicsEntity(TransformComponent transform, RenderComponent mesh, RigidBodyComponent rigidBody) {
        super(transform, mesh);
        this.add(rigidBody);
        this.rigidBodyCom = this.getComponent(RigidBodyComponent.class);
    }
}
