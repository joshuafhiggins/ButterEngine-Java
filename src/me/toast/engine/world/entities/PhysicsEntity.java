package me.toast.engine.world.entities;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;
import me.toast.engine.Mod;
import me.toast.engine.rendering.Mesh;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static me.toast.engine.utils.MathConv.ToJOML;

public class PhysicsEntity extends RenderEntity {

    public RigidBody rigidBody;

    private final Matrix4f workingMatrixJOML = new Matrix4f();
    private final javax.vecmath.Matrix4f tempMatrixVecmath = new javax.vecmath.Matrix4f();
    private final com.bulletphysics.linearmath.Transform tempTransform = new Transform();

    public PhysicsEntity(Vector3f position, Quaternionf rotation, Vector3f scale, Mesh mesh, RigidBody body) {
        super(position, rotation, scale, mesh);
        this.rigidBody = body;
    }

    @Override
    public void Start() {
        Mod.LOADED_MOD.JBullet.AddRigidBody(rigidBody);
        super.Start();
    }

    @Override
    public void Update() {
        ToJOML(rigidBody.getWorldTransform(tempTransform).getMatrix(tempMatrixVecmath), workingMatrixJOML);
        super.Update();
    }

    @Override
    public void Destroy() {
        Mod.LOADED_MOD.JBullet.RemoveRigidBody(rigidBody);
        super.Destroy();
    }
}
