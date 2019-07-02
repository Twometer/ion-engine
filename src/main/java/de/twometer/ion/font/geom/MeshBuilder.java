package de.twometer.ion.font.geom;

import de.twometer.ion.mesh.Mesh;

public class MeshBuilder {

    private Mesh mesh;

    public MeshBuilder(int capacity) {
        this.mesh = new Mesh(capacity, 2).withTexCoords();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void putRectVertices(Rectangle rectangle) {
        putRectVertices(rectangle.getX1(), rectangle.getY1(), rectangle.getX2(), rectangle.getY2());
    }

    private void putRectVertices(float x1, float y1, float x2, float y2) {
        mesh.putVertex(x1, y1);
        mesh.putVertex(x1, y2);
        mesh.putVertex(x2, y2);
        mesh.putVertex(x2, y2);
        mesh.putVertex(x2, y1);
        mesh.putVertex(x1, y1);
    }

    public void putRectColors(float r, float g, float b) {
        for (int i = 0; i < 6; i++)
            mesh.putColor(r, g, b);
    }

    public void putRectTexCoords(float u1, float v1, float u2, float v2) {
        mesh.putTexCoord(u1, v1);
        mesh.putTexCoord(u1, v2);
        mesh.putTexCoord(u2, v2);
        mesh.putTexCoord(u2, v2);
        mesh.putTexCoord(u2, v1);
        mesh.putTexCoord(u1, v1);
    }

    public void destroy() {
        mesh.destroy();
    }

}
