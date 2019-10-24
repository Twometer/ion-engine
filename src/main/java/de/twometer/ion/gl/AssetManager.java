package de.twometer.ion.gl;

import de.twometer.ion.IonException;
import de.twometer.ion.mesh.Model;
import de.twometer.ion.res.Loader;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static Map<String, Model> modelCache = new HashMap<>();

    private static Map<String, Texture> textureCache = new HashMap<>();

    private static Map<Class<? extends Shader>, Shader> shaderCache = new HashMap<>();

    /**
     * Get a shader from the cache, or load it from resources if it is not present
     *
     * @param shaderClass The class of the shader
     * @param <T>         The type of the shader
     * @return The shader's instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends Shader> T getShader(Class<T> shaderClass) {
        try {
            if (!shaderCache.containsKey(shaderClass)) {
                T shader;
                shader = shaderClass.newInstance();
                shaderCache.put(shaderClass, shader);
                return shader;
            }
            return (T) shaderCache.get(shaderClass);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IonException(e);
        }
    }

    /**
     * Get a model from the cache, or load it from resources if it is not present.
     *
     * @param texture The name of the image file
     * @return A loaded OpenGL texture
     */
    public static Texture getTexture(String texture) {
        if (textureCache.containsKey(texture))
            return textureCache.get(texture);
        Texture t = Loader.loadTexture(texture);
        textureCache.put(texture, t);
        return t;
    }

    /**
     * Get a model from the cache, or load it from resources if it is not present.
     *
     * @param model The name of the obj file
     * @return A loaded OpenGL model
     */
    public static Model getModel(String model) {
        if (modelCache.containsKey(model))
            return modelCache.get(model);
        Model m = Loader.loadObj(model);
        modelCache.put(model, m);
        return m;
    }

}
