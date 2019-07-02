package de.twometer.ion.font;

import de.twometer.ion.core.IonApp;
import de.twometer.ion.font.geom.MeshBuilder;
import de.twometer.ion.font.geom.Rectangle;
import de.twometer.ion.mesh.Model;
import org.joml.Vector4f;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class FontRenderer {

    private FontShader fontShader;

    private FontFace fontFace;

    private Model textModel;

    private IonApp app;

    public FontRenderer(IonApp app, String fontFace) {
        this.app = app;
        fontShader = new FontShader();
        try {
            this.fontFace = FontFace.load(fontFace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(String text, float x, float y, float fontSize, Vector4f color) {
        textModel = build(text, fontSize, x, y);
        fontShader.bind();
        fontShader.setProjectionMatrix(app.getWindow().getGuiMatrix());
        fontShader.setColor(color.x, color.y, color.z, color.w);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, fontFace.getFontTexture());
        textModel.draw();
        glBindTexture(GL_TEXTURE_2D, 0);
        fontShader.unbind();
        textModel.destroy();
    }

    public void destroy() {
        if (textModel != null)
            textModel.destroy();
    }

    private Model build(String text, float fontSize, float x, float y) {
        MeshBuilder meshBuilder = new MeshBuilder(text.length() * 6);

        float cursor = x;
        for (char c : text.toCharArray()) {
            Glyph glyph = fontFace.getGlyph(c);
            if (glyph == null)
                continue;

            Rectangle charRect = createChar(cursor, y, glyph, fontSize);
            meshBuilder.putRectVertices(charRect);

            float w = 512;
            float h = 512;
            meshBuilder.putRectTexCoords((glyph.getX()) / w, (glyph.getY()) / h, (glyph.getX() + glyph.getWidth()) / w, (glyph.getY() + glyph.getHeight()) / h);
            cursor += (glyph.getAdvance() - 15) * fontSize;
        }

        Model model = Model.create(meshBuilder.getMesh(), GL_TRIANGLES);
        meshBuilder.destroy();
        return model;
    }

    private Rectangle createChar(float cursorX, float cursorY, Glyph glyph, float fontSize) {
        float x1 = cursorX + (glyph.getxOffset() * fontSize);
        float y1 = cursorY + (glyph.getyOffset() * fontSize);
        float x2 = x1 + (glyph.getWidth() * fontSize);
        float y2 = y1 + (glyph.getHeight() * fontSize);
        return new Rectangle(x1, y1, x2, y2);
    }

}
