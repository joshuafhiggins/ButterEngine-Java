package me.toast.engine.ecs.components;

import com.badlogic.ashley.core.Component;
import me.toast.engine.rendering.Mesh;

public class RenderComponent implements Component {

    public Mesh mesh;

    public RenderComponent(Mesh mesh) {
        this.mesh = mesh;
    }
}
