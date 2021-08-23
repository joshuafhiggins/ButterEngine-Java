package me.toast.engine.physics;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import com.bulletphysics.util.*;
import org.joml.*;

import me.toast.engine.world.components.Transform;

import static me.toast.engine.utils.MathConv.*;

public class RigidBodyBuilder {
    private CollisionShape shape;
    private MotionState motionState;
    private RigidBodyConstructionInfo construction;

    //All shapes' inputs are in local space
    public RigidBodyBuilder SetPlane(Vector3f normal, float bufferHeight) {
        shape = new StaticPlaneShape(ToVecmath(normal, new javax.vecmath.Vector3f()), bufferHeight);
        return this;
    }
    public RigidBodyBuilder SetBox(Vector3f size) {
        //Half extent is half of size
        shape = new BoxShape(ToVecmath(size.div(2f, new Vector3f()), new javax.vecmath.Vector3f()));
        return this;
    }
    public RigidBodyBuilder SetSphere(float radius) {
        shape = new SphereShape(radius);
        return this;
    }
    public RigidBodyBuilder SetCapsule(float radius, float height) {
        shape = new CapsuleShape(radius, height);
        return this;
    }
    public RigidBodyBuilder SetCylinder(Vector3f size) {
        //Half extent is half of size
        shape = new CylinderShape(ToVecmath(size.div(2f, new Vector3f()), new javax.vecmath.Vector3f()));
        return this;
    }
    public RigidBodyBuilder SetCone(float radius, float heightOfPoint) {
        shape = new ConeShape(radius, heightOfPoint);
        return this;
    }
    public RigidBodyBuilder SetConvexHull(Vector3f[] points) {
        ConvexHullShape hullShape = new ConvexHullShape(new ObjectArrayList<>());
        for (Vector3f point : points)
            hullShape.addPoint(ToVecmath(point, new javax.vecmath.Vector3f()));
        shape = hullShape;
        return this;
    }
    //TODO: public RigidBodyBuilder SetCompoundShape() {}

    public RigidBodyBuilder SetMotion(Transform transform) {
        motionState = new DefaultMotionState(new com.bulletphysics.linearmath.Transform(ToVecmath(transform.getModelMatrix(), new javax.vecmath.Matrix4f())));
        return this;
    }

    public RigidBodyBuilder SetConstruction(Vector3f baseInertia, float mass, float restitution, float friction) {
        construction = new RigidBodyConstructionInfo(mass, motionState, shape, ToVecmath(baseInertia, new javax.vecmath.Vector3f()));
        construction.restitution = restitution;
        construction.friction = friction;
        return this;
    }

    public RigidBody Build() {
        return new RigidBody(construction);
    }
}
