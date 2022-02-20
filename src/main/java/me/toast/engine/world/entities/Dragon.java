package me.toast.engine.world.entities;

import com.badlogic.ashley.core.Entity;
import me.toast.engine.AssetPool;
import me.toast.engine.Mod;
import me.toast.engine.rendering.Model;
import me.toast.engine.world.components.MeshComponent;
import me.toast.engine.world.components.TransformComponent;

public class Dragon extends Entity {

    public MeshComponent meshCom;
    public TransformComponent transform;

    public Dragon(TransformComponent transform) {
        this.add(transform);
        this.add(new MeshComponent(Model.LoadScene("dragon.obj", AssetPool.getShader("Mesh"), AssetPool.getMaterial("dragon"))[0]));

        this.transform = getComponent(TransformComponent.class);
        this.meshCom = this.getComponent(MeshComponent.class);

        Mod.Ashley.addEntity(this);
    }
}
