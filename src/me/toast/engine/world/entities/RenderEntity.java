package me.toast.engine.world.entities;

import me.toast.engine.Mod;
import me.toast.engine.rendering.Mesh;
import me.toast.engine.world.components.Transform;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RenderEntity extends BaseEntity {

    public Mesh mesh;

    public RenderEntity(Vector3f position, Quaternionf rotation, Vector3f scale, Mesh mesh) {
        super(new Transform(position, rotation, scale));
        this.mesh = mesh;
    }

    @Override
    public void Render() {
        mesh.Render(transform, Mod.LOADED_MOD.Camera);
        super.Render();
    }

    @Override
    public void Destroy() {
        mesh.Destroy();
        super.Destroy();
    }
}
