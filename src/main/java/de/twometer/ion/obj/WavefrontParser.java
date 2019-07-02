package de.twometer.ion.obj;

import de.twometer.ion.mesh.Mesh;
import de.twometer.ion.obj.geom.Face;
import de.twometer.ion.obj.geom.VertexRef;
import de.twometer.ion.obj.material.Material;
import de.twometer.ion.obj.material.MaterialLib;
import de.twometer.ion.res.ResourceLoader;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class WavefrontParser {

    private static final String MATERIAL_LIB = "mtllib";
    private static final String USE_MATERIAL = "usemtl";
    private static final String OBJECT = "o";
    private static final String VERTEX = "v";
    private static final String VERTEX_NORMAL = "vn";
    private static final String FACE = "f";

    private String workingDirectory;

    private String modelName;

    private MaterialLib materialLib;

    private List<WavefrontObject> objects = new ArrayList<>();

    private List<Vector3f> vertices = new ArrayList<>();

    private List<Vector3f> normals = new ArrayList<>();

    public WavefrontParser(String workingDirectory, String modelName) {
        this.workingDirectory = workingDirectory;
        this.modelName = modelName;
    }

    public Mesh createMesh() throws IOException {
        parse();
        int totalVertices = 0;
        for (WavefrontObject object : objects) totalVertices += object.getFaces().size() * 3;

        Mesh mesh = new Mesh(totalVertices, 3).withNormals();
        for (WavefrontObject object : objects) {
            Material material = materialLib.getMaterial(object.getMaterial());
            Vector3f color = material.getDiffuseColor();

            for (Face face : object.getFaces()) {
                for (VertexRef ref : face.getVertices()) {
                    Vector3f vertex = vertices.get(ref.getVertex() - 1);
                    Vector3f normal = normals.get(ref.getNormal() - 1);
                    mesh.putVertex(vertex.x, vertex.y, vertex.z);
                    mesh.putColor(color.x, color.y, color.z);
                    mesh.putNormal(normal.x, normal.y, normal.z);
                }
            }

        }
        return mesh;
    }

    private void parse() throws IOException {
        BufferedReader reader = new BufferedReader(ResourceLoader.openReader(workingDirectory + modelName));

        WavefrontObject currentObject = null;

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() == 0) continue;
            if (line.startsWith("#")) continue;
            if (!line.contains(" ")) continue;

            String[] parts = line.split(" ");
            String instruction = parts[0];

            switch (instruction) {
                case MATERIAL_LIB:
                    Reader mtlReader = ResourceLoader.openReader(workingDirectory + parts[1]);
                    materialLib = new MaterialLib();
                    materialLib.load(mtlReader);
                    break;
                case OBJECT:
                    currentObject = new WavefrontObject(parts[1]);
                    objects.add(currentObject);
                    break;
                case USE_MATERIAL:
                    if (currentObject != null) currentObject.setMaterial(parts[1]);
                    else throw new IllegalStateException("Malformed OBJ");
                    break;
                case VERTEX:
                    vertices.add(parseVector(parts[1], parts[2], parts[3]));
                    break;
                case VERTEX_NORMAL:
                    normals.add(parseVector(parts[1], parts[2], parts[3]));
                    break;
                case FACE:
                    VertexRef[] refs = new VertexRef[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        String[] vert = parts[i].split("//");
                        VertexRef ref = new VertexRef(Integer.parseInt(vert[0]), Integer.parseInt(vert[1]));
                        refs[i - 1] = ref;
                    }
                    if (currentObject != null) currentObject.getFaces().add(new Face(refs));
                    else throw new IllegalStateException("Malformed OBJ");
                    break;
            }

        }
    }


    private Vector3f parseVector(String x, String y, String z) {
        float xf = Float.parseFloat(x);
        float yf = Float.parseFloat(y);
        float zf = Float.parseFloat(z);
        return new Vector3f(xf, yf, zf);
    }

}
