package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

/**
 * Dynamic proxy that adapts a {@link java.util.function.Supplier} to a {@link Randomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class RandomizerProxy implements InvocationHandler {

    private Supplier<?> target;

    RandomizerProxy(final Supplier<?> target) {
        this.target = target;
    }

    /**
     * Create a dynamic proxy that adapts the given {@link Supplier} to a {@link Randomizer}.
     * @param supplier to adapt
     * @return the proxy randomizer
     */
    static Randomizer<?> asRandomizer(final Supplier<?> supplier) {
        return (Randomizer<?>) Proxy.newProxyInstance(
                Randomizer.class.getClassLoader(),
                new Class[]{Randomizer.class},
                new RandomizerProxy(supplier));
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if ("getRandomValue".equals(method.getName())) {
            return target.getClass().getMethod("get").invoke(target, args);
        }
        return null;
    }
}