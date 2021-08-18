package me.toast.engine.physics;

import com.bulletphysics.collision.broadphase.*;
import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.*;
import com.bulletphysics.linearmath.*;
import com.bulletphysics.util.*;
import org.joml.*;

import me.toast.engine.ecs.components.TransformComponent;

import static me.toast.engine.utils.MathConv.*;

public class Physics {

    //Normally this would be public, but because of the math shenanigans I'm using JOML as input and this class handles it
    private DynamicsWorld world;

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

    public void Shutdown() {
        world.destroy();
    }

    public static class RigidBodyBuilder {
        CollisionShape shape;
        MotionState motionState;
        RigidBodyConstructionInfo construction;

        //All shapes' inputs are in local space
        public void SetPlane(Vector3f normal, float bufferHeight) {
            shape = new StaticPlaneShape(ToVecmath(normal, new javax.vecmath.Vector3f()), bufferHeight);
        }
        public void SetBox(Vector3f size) {
            //Half extent is half of size
            shape = new BoxShape(ToVecmath(size.div(2f, new Vector3f()), new javax.vecmath.Vector3f()));
        }
        public void SetSphere(float radius) {
            shape = new SphereShape(radius);
        }
        public void SetCapsule(float radius, float height) {
            shape = new CapsuleShape(radius, height);
        }
        public void SetCylinder(Vector3f size) {
            //Half extent is half of size
            shape = new CylinderShape(ToVecmath(size.div(2f, new Vector3f()), new javax.vecmath.Vector3f()));
        }
        public void SetCone(float radius, float heightOfPoint) {
            shape = new ConeShape(radius, heightOfPoint);
        }
        public void SetConvexHull(Vector3f[] points) {
            ConvexHullShape hullShape = new ConvexHullShape(new ObjectArrayList<>());
            for (Vector3f point : points)
                hullShape.addPoint(ToVecmath(point, new javax.vecmath.Vector3f()));
            shape = hullShape;
        }
        //TODO: public void SetCompoundShape() {}

        public void SetMotion(TransformComponent transform) {
            motionState = new DefaultMotionState(new Transform(ToVecmath(transform.getModelMatrix(), new javax.vecmath.Matrix4f())));
        }

        public void SetConstruction(Vector3f baseInertia, float mass, float restitution, float friction) {
            construction = new RigidBodyConstructionInfo(mass, motionState, shape, ToVecmath(baseInertia, new javax.vecmath.Vector3f()));
            construction.restitution = restitution;
            construction.friction = friction;
        }

        public RigidBody Build() {
            return new RigidBody(construction);
        }
    }
}
