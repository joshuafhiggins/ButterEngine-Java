package me.toast.engine.physics;

import com.bulletphysics.collision.broadphase.*;
import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.*;
import com.bulletphysics.linearmath.*;
import org.joml.*;

import static me.toast.engine.utils.MathConv.*;

//TODO: Look into changing math libraries or reworking JBullet to use JOML
public class Physics {

    //Normally this would be public, but because of the math shenanigans I'm using JOML as input and this class handles it
    private DynamicsWorld world;

    public Physics() {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();
        world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        world.setGravity(ToVecmath(new Vector3f(0, -10 /* m/s2 */, 0), new javax.vecmath.Vector3f()));

        CollisionShape groundShape = new StaticPlaneShape(ToVecmath(new Vector3f(0, 1, 0), new javax.vecmath.Vector3f()), 0.25f /* m */);
        Matrix4f groundMat = new Matrix4f().translate(new Vector3f()).rotate(new Quaternionf(0, 0, 1, 0)).scale(1);
        MotionState groundMotionState = new DefaultMotionState(new Transform(ToVecmath(groundMat, new javax.vecmath.Matrix4f())));
        RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(
                0, groundMotionState, groundShape,
                ToVecmath(new Vector3f(0),
                new javax.vecmath.Vector3f()));
        groundBodyConstructionInfo.restitution = 0.25f;
        RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
        world.addRigidBody(groundRigidBody);
    }

    public void Update() {
        //TODO: Delta Time?
        world.stepSimulation(1f/60f);
    }

    public void AddRigidBody(RigidBody body) {
        world.addRigidBody(body);
    }

    public void Shutdown() {
        world.destroy();
    }
}
