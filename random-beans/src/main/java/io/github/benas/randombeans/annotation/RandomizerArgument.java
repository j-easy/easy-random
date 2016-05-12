package io.github.benas.randombeans.annotation;

/**
 * Created by dkopel on 5/11/16.
 */
public @interface RandomizerArgument {
    String value() default "";
    Class type() default Object.class;
}