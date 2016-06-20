package io.github.benas.randombeans.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dkopel on 6/20/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface FakeExclude {
}
