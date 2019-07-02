package de.twometer.ion.font;

import de.twometer.ion.gl.Shader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class FontShader extends Shader {

    private int projectionMatrix;

    private int color;

    FontShader() {
        super("font");
    }

    @Override
    protected void bindUniforms(int program) {
        this.projectionMatrix = glGetUniformLocation(program, "projectionMatrix");
        this.color = glGetUniformLocation(program, "color");
    }

    void setProjectionMatrix(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(projectionMatrix, false, buffer);
    }

    void setColor(float r, float g, float b, float a) {
        glUniform4f(color, r, g, b, a);
    }

}
