package de.twometer.ion.gl;

import de.twometer.ion.res.Loader;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;

public abstract class Shader {

    private static final String VERT_SHADER_EXT = ".v.glsl";
    private static final String FRAG_SHADER_EXT = ".f.glsl";

    private int programId;

    public Shader(String name) {
        String vertexPath = "shaders/" + name + VERT_SHADER_EXT;
        String fragmentPath = "shaders/" + name + FRAG_SHADER_EXT;
        programId = Loader.loadShader(vertexPath, fragmentPath);
        initialize();
        System.out.printf("Loaded shader '%s'%n", name);
    }

    private void initialize() {
        bind();
        bindUniforms(programId);
        unbind();
    }

    /**
     * Binds the shader program
     */
    public final void bind() {
        glUseProgram(programId);
    }

    /**
     * Unbinds the shader program
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Releases all used native resources
     */
    public final void destroy() {
        glDeleteProgram(programId);
    }

    /**
     * Asks subclasses to initialize themselves and bind their
     * uniform locations
     *
     * @param program The current program id
     */
    protected abstract void bindUniforms(int program);

}

