package me.toast.engine.util;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class MathConv {

    public static org.joml.Vector3f ToJOML(javax.vecmath.Vector3f in,  org.joml.Vector3f out) {
        out.set(in.x, in.y, in.z);
        return out;
    }

    public static javax.vecmath.Vector3f ToVecmath(org.joml.Vector3f in, javax.vecmath.Vector3f out) {
        out.set(in.x, in.y, in.z);
        return out;
    }

    public static org.joml.Matrix4f ToJOML(javax.vecmath.Matrix4f in,  org.joml.Matrix4f out) {
        out.set(in.m00, in.m10, in.m20, in.m30,
                in.m01, in.m11, in.m21, in.m31,
                in.m02, in.m12, in.m22, in.m32,
                in.m03, in.m13, in.m23, in.m33);
        return out;
    }

    public static javax.vecmath.Matrix4f ToVecmath(org.joml.Matrix4f in, javax.vecmath.Matrix4f out) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        in.get(buffer);
        buffer.flip();
        out.set(buffer.array());
        return out;
    }
}
