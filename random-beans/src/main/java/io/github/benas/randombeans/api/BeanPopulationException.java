package io.github.benas.randombeans.api;

/**
 * Exception thrown when Random Beans is unable to populate an instance of a given type.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class BeanPopulationException extends Exception {

    public BeanPopulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
