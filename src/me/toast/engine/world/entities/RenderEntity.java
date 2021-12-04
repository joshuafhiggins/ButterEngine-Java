package me.toast.engine.world.entities;

import me.toast.engine.world.components.RenderComponent;
import me.toast.engine.world.components.TransformComponent;

public class RenderEntity extends PositionEntity {

    public RenderComponent meshCom;

    public RenderEntity(TransformComponent transform, RenderComponent mesh) {
        super(transform);
        this.add(mesh);
        this.meshCom = this.getComponent(RenderComponent.class);
    }
}
