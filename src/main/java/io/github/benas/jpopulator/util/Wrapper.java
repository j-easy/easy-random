package io.github.benas.jpopulator.util;

/**
 * Interface to wrap som elements into another.
 *
 * @param  <T> Element Type to wrap.
 * @param  <R> Wrapped Element Type.
 *
 * @author mertinat
 * @since  18.12.2015
 */
public interface Wrapper<T, R> {
    R wrap(final T backreference);
}
