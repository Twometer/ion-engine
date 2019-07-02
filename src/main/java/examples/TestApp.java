package examples;

import de.twometer.ion.core.IonApp;
import de.twometer.ion.annotations.Window;
import de.twometer.ion.font.Color;
import de.twometer.ion.font.FontRenderer;

import static org.lwjgl.opengl.GL11.*;

@Window(title = "Ion Engine Test Application")
public class TestApp extends IonApp {

    private FontRenderer fontRenderer;

    @Override
    public void onPreInit() {

    }

    @Override
    public void onInit() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        fontRenderer = new FontRenderer(this, "lucida");
    }

    @Override
    public void onDraw() {
        fontRenderer.draw("Hello world!", 10, 10, 1.0f, Color.WHITE);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onShutdown() {

    }

}
