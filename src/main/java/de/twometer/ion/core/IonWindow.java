package de.twometer.ion.core;

import de.twometer.ion.IonException;
import de.twometer.ion.res.Loader;
import de.twometer.ion.res.ResourceLoader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class IonWindow {

    private long window;

    private int width;

    private int height;

    private String title;

    private Matrix4f guiMatrix = new Matrix4f();

    void create(int glMajor, int glMinor, int samples) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, glMajor);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, glMinor);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_SAMPLES, samples);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
    }

    public int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

    void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    void update() {
        guiMatrix.setOrtho2D(0, getWidth(), getHeight(), 0);
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public Matrix4f getGuiMatrix() {
        return guiMatrix;
    }

    public void setIcon(String resourcePath) {
        try {
            BufferedImage image = ResourceLoader.loadImage(resourcePath);
            ByteBuffer buffer = Loader.loadPixels(image);

            GLFWImage.Buffer icons = GLFWImage.malloc(1);
            icons.position(0)
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .pixels(buffer);
            glfwSetWindowIcon(window, icons);
            icons.free();
        } catch (IOException e) {
            throw new IonException(e);
        }
    }

    public Vector2f getCursorPosition() {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        glfwGetCursorPos(window, xPos, yPos);
        return new Vector2f((float) xPos[0], (float) yPos[0]);
    }

    public boolean isMouseButtonPressed(int mouseButton) {
        return glfwGetMouseButton(window, mouseButton) == GLFW_PRESS;
    }

    public boolean isKeyPressed(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

}
