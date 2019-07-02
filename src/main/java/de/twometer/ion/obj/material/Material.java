package de.twometer.ion.obj.material;

import org.joml.Vector3f;

/**
 * Created by Twometer on 07.04.2017.
 * (c) 2017 Twometer
 */
public class Material {

    private String name;

    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;

    private float shininess;
    private float transparency;

    private int illumination;

    Material(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }

    void setDiffuseColor(Vector3f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector3f getSpecularColor() {
        return specularColor;
    }

    void setSpecularColor(Vector3f specularColor) {
        this.specularColor = specularColor;
    }

    public float getShininess() {
        return shininess;
    }

    void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getTransparency() {
        return transparency;
    }

    void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public int getIllumination() {
        return illumination;
    }

    void setIllumination(int illumination) {
        this.illumination = illumination;
    }
}
