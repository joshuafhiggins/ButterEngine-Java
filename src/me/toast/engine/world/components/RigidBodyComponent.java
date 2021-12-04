package me.toast.engine.world.components;

import com.badlogic.ashley.core.Component;
import com.bulletphysics.dynamics.RigidBody;

public class RigidBodyComponent implements Component {

    public RigidBody rigidBody;

    public RigidBodyComponent(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }
}
