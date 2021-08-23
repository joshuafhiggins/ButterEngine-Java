package me.toast.engine.world.entities;

import me.toast.engine.world.components.Transform;

public class BaseEntity {

    public Transform transform;

    public BaseEntity(Transform transform) {
        this.transform = transform;
        Start();
    }

    public void Start() {}
    public void Update() {}
    public void Render() {}
    public void Destroy() {}
}
