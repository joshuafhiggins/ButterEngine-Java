package me.toast.engine.ecs.entities;

import com.bulletphysics.dynamics.RigidBody;
import me.toast.engine.ecs.components.RigidBodyComponent;

public class PropEntity extends RenderEntity {

    public RigidBodyComponent rigidBodyComponent;

    public PropEntity(RenderEntity base, RigidBody body) {
        super(base.transformComponent, base.renderComponent);
        rigidBodyComponent = addAndReturn(new RigidBodyComponent(body));
    }
}
