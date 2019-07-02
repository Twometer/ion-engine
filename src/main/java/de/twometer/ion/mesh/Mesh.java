package de.twometer.ion.mesh;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {

    private int vertexCapacity;

    private FloatBuffer vertices;

    private FloatBuffer colors;

    private FloatBuffer normals;

    private FloatBuffer texCoords;

    private int vertexCount;

    private int colorCount;

    private int normalCount;

    private int texCoordCount;

    private int dimensions;

    public Mesh(int vertexCapacity, int dimensions) {
        this.vertexCapacity = vertexCapacity;
        this.dimensions = dimensions;
        vertices = MemoryUtil.memAllocFloat(vertexCapacity * dimensions);
    }

    public Mesh withColors() {
        colors = MemoryUtil.memAllocFloat(vertexCapacity * 3);
        return this;
    }

    public Mesh withNormals() {
        normals = MemoryUtil.memAllocFloat(vertexCapacity * dimensions);
        return this;
    }

    public Mesh withTexCoords() {
        texCoords = MemoryUtil.memAllocFloat(vertexCapacity * 2);
        return this;
    }

    public void putVertex(float x, float y, float z) {
        vertices.put(x);
        vertices.put(y);
        vertices.put(z);
        vertexCount++;
    }

    public void putVertex(float x, float y) {
        vertices.put(x);
        vertices.put(y);
        vertexCount++;
    }

    public void putColor(float r, float g, float b) {
        colors.put(r);
        colors.put(g);
        colors.put(b);
        colorCount++;
    }

    public void putNormal(float x, float y, float z) {
        normals.put(x);
        normals.put(y);
        normals.put(z);
        normalCount++;
    }

    public void putNormal(float x, float y) {
        normals.put(x);
        normals.put(y);
        normalCount++;
    }

    public void putTexCoord(float u, float v) {
        texCoords.put(u);
        texCoords.put(v);
        texCoordCount++;
    }

    int getVertexCount() {
        return vertexCount;
    }

    int getColorCount() {
        return colorCount;
    }

    int getTexCoordCount() {
        return texCoordCount;
    }

    int getNormalCount() {
        return normalCount;
    }

    FloatBuffer getVertices() {
        return vertices;
    }

    FloatBuffer getColors() {
        return colors;
    }

    FloatBuffer getTexCoords() {
        return texCoords;
    }

    FloatBuffer getNormals() {
        return normals;
    }

    public void destroy() {
        MemoryUtil.memFree(vertices);
        if (colors != null) MemoryUtil.memFree(colors);
        if (normals != null) MemoryUtil.memFree(normals);
        if (texCoords != null) MemoryUtil.memFree(texCoords);
    }

    int getDimensions() {
        return dimensions;
    }

}
