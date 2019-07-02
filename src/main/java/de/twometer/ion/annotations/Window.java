package de.twometer.ion.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Window {

    int width() default 800;

    int height() default 600;

    String title() default "Ion Engine";

    int glMajor() default 3;

    int glMinor() default 3;

    int samples() default 8;

}
