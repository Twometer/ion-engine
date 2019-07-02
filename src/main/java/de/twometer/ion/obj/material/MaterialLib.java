package de.twometer.ion.obj.material;

import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * Created by Twometer on 07.04.2017.
 * (c) 2017 Twometer
 */
public class MaterialLib {

    private HashMap<String, Material> materials;

    private void addMaterial(Material material) {
        materials.put(material.getName(), material);
    }

    public Material getMaterial(String name) {
        return materials.get(name);
    }

    public void load(Reader inputReader) throws IOException {
        materials = new HashMap<>();
        Material currentMaterial = null;

        BufferedReader reader = new BufferedReader(inputReader);
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() == 0) continue;
            if (line.startsWith("#")) continue;
            if (!line.contains(" ")) continue;

            String[] parts = line.split(" ");
            String instruction = parts[0];

            switch (instruction) {
                case "newmtl":
                    currentMaterial = new Material(parts[1]);
                    addMaterial(currentMaterial);
                    break;
                case "Kd":
                    if (currentMaterial != null)
                        currentMaterial.setDiffuseColor(parseColor(parts));
                    break;
                case "Ka":
                    if (currentMaterial != null)
                        currentMaterial.setAmbientColor(parseColor(parts));
                    break;
                case "Ks":
                    if (currentMaterial != null)
                        currentMaterial.setSpecularColor(parseColor(parts));
                    break;
                case "Ns":
                    if (currentMaterial != null)
                        currentMaterial.setShininess(Float.parseFloat(parts[1]));
                    break;
                case "d":
                    if (currentMaterial != null)
                        currentMaterial.setTransparency(Float.parseFloat(parts[1]));
                    break;
                case "illum":
                    if (currentMaterial != null)
                        currentMaterial.setIllumination(Integer.parseInt(parts[1]));
                    break;

            }
        }
        reader.close();
    }

    private Vector3f parseColor(String[] parts) {
        float rf = Float.parseFloat(parts[1]);
        float gf = Float.parseFloat(parts[2]);
        float bf = Float.parseFloat(parts[3]);
        return new Vector3f(rf, gf, bf);
    }


}
