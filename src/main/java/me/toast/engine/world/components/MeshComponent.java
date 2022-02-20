package me.toast.engine.world.components;

import com.badlogic.ashley.core.Component;
import me.toast.engine.rendering.Mesh;

public class MeshComponent implements Component {

    public Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }
}
