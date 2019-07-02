package de.twometer.ion.obj.geom;

public class VertexRef {

    private int vertex;

    private int normal;

    public VertexRef(int vertex, int normal) {
        this.vertex = vertex;
        this.normal = normal;
    }

    public int getVertex() {
        return vertex;
    }

    public int getNormal() {
        return normal;
    }

}
