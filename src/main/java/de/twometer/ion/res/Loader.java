package de.twometer.ion.res;

import de.twometer.ion.IonException;
import de.twometer.ion.gl.Texture;
import de.twometer.ion.mesh.Mesh;
import de.twometer.ion.mesh.Model;
import de.twometer.ion.obj.WavefrontParser;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Loader {

    private static final int BYTES_PER_PIXEL = 4;

    public static int loadShader(String vertPath, String fragPath) {
        try {
            int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
            int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);

            String vertexShaderCode = ResourceLoader.loadString(vertPath);
            String fragmentShaderCode = ResourceLoader.loadString(fragPath);

            System.out.println("Compiling vertex shader");
            glShaderSource(vertexShaderId, vertexShaderCode);
            glCompileShader(vertexShaderId);
            checkShaderError(vertexShaderId);

            System.out.println("Compiling fragment shader");
            glShaderSource(fragmentShaderId, fragmentShaderCode);
            glCompileShader(fragmentShaderId);
            checkShaderError(fragmentShaderId);

            int programId = glCreateProgram();
            glAttachShader(programId, vertexShaderId);
            glAttachShader(programId, fragmentShaderId);
            glLinkProgram(programId);

            // After the shader program is linked, the shader sources can be cleaned up
            glDetachShader(programId, vertexShaderId);
            glDetachShader(programId, fragmentShaderId);
            glDeleteShader(vertexShaderId);
            glDeleteShader(fragmentShaderId);

            return programId;
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public static Texture loadTexture(String path) {
        try {
            BufferedImage image = ResourceLoader.loadImage(path);
            ByteBuffer buffer = loadPixels(image);

            int textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glBindTexture(GL_TEXTURE_2D, 0);

            return new Texture(textureId, image.getWidth(), image.getHeight());
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public static ByteBuffer loadPixels(BufferedImage image) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), true);
                buffer.put((byte) color.getRed());
                buffer.put((byte) color.getGreen());
                buffer.put((byte) color.getBlue());
                buffer.put((byte) color.getAlpha());
            }
        }
        buffer.flip();
        return buffer;
    }

    public static Model loadObj(String model) {
        WavefrontParser parser = new WavefrontParser("models/", model);
        try {
            Mesh mesh = parser.createMesh();
            Model mdl = Model.create(mesh, GL_TRIANGLES);
            mesh.destroy();
            return mdl;
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    private static void checkShaderError(int shaderId) {
        String log = glGetShaderInfoLog(shaderId);
        if (log.length() > 0)
            System.out.println(log);
    }

}
