package me.toast.engine.physics;

import com.bulletphysics.collision.broadphase.*;
import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.*;
import org.joml.*;

import static me.toast.engine.utils.MathConv.*;

public class Physics {

    //Normally this would be public, but because of the math shenanigans I'm using JOML as input and this class handles it
    private DynamicsWorld world;

    public static final RigidBodyBuilder BUILDER = new RigidBodyBuilder();

    public Physics() {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();
        world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        SetGravity(new Vector3f(0, -10 /* m/s2 */, 0));
    }

    public void SetGravity(Vector3f gravity) {
        world.setGravity(ToVecmath(gravity, new javax.vecmath.Vector3f()));
    }

    public void Update() {
        //TODO: Delta Time?
        world.stepSimulation(1f/60f);
    }

    public void AddRigidBody(RigidBody body) {
        world.addRigidBody(body);
    }

    public void RemoveRigidBody(RigidBody rigidBody) {
        world.removeRigidBody(rigidBody);
    }

    public void Destroy() {
        world.destroy();
    }
}
