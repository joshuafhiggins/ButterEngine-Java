package me.toast.engine.world.entities;

import com.badlogic.ashley.core.Entity;
import me.toast.engine.Mod;
import me.toast.engine.world.components.TransformComponent;

public class PositionEntity extends Entity {

    public TransformComponent transform;

    public PositionEntity(TransformComponent transform) {
        this.add(transform);
        this.transform = getComponent(TransformComponent.class);
        Mod.LOADED_MOD.Ashley.addEntity(this);
    }
}
