package io.github.benas.jpopulator.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to exclude a field from being populated.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}
