package de.twometer.ion.gl;

import de.twometer.ion.IonException;
import de.twometer.ion.res.Loader;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    private static Map<String, Texture> textureCache = new HashMap<>();

    private static Map<Class<? extends Shader>, Shader> shaderCache = new HashMap<>();

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

    public static Texture getTexture(String texture) {
        if(textureCache.containsKey(texture))
            return textureCache.get(texture);
        Texture t = Loader.loadTexture(texture);
        textureCache.put(texture, t);
        return t;
    }

}
