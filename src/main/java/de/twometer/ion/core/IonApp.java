package de.twometer.ion.core;

import de.twometer.ion.annotations.Window;
import de.twometer.ion.util.FboHelper;

public abstract class IonApp {

    private IonWindow window = new IonWindow();

    private FboHelper helper = new FboHelper();

    public final void launch() {
        onPreInit();
        Window annotation = getClass().getAnnotation(Window.class);
        if (annotation == null)
            throw new IllegalStateException("IonApp " + getClass().getName() + " is missing the Window annotation");
        window.setHeight(annotation.height());
        window.setWidth(annotation.width());
        window.setTitle(annotation.title());
        window.create(annotation.glMajor(), annotation.glMinor(), annotation.samples());
        helper.initialize();
        onInit();
        while (!window.isCloseRequested()) {
            onDraw();
            window.update();
        }
        onShutdown();
        window.destroy();
    }

    public abstract void onPreInit();

    public abstract void onInit();

    public abstract void onDraw();

    public abstract void onUpdate();

    public abstract void onShutdown();

    public IonWindow getWindow() {
        return window;
    }

    public FboHelper getHelper() {
        return helper;
    }
}
