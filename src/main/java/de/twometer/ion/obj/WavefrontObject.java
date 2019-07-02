package de.twometer.ion.obj;


import de.twometer.ion.obj.geom.Face;

import java.util.ArrayList;
import java.util.List;

public class WavefrontObject {

    private String name;

    private String material;

    private List<Face> faces = new ArrayList<>();

    WavefrontObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    void setMaterial(String material) {
        this.material = material;
    }

    List<Face> getFaces() {
        return faces;
    }

}
