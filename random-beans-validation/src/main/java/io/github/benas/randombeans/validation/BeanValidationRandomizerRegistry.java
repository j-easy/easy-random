package io.github.benas.randombeans.validation;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static io.github.benas.randombeans.validation.ReflectionUtils.getReadMethod;
import static io.github.benas.randombeans.validation.ReflectionUtils.isAnnotationPresent;

@Priority(-2)
public class BeanValidationRandomizerRegistry implements RandomizerRegistry {

    private Map<Class<? extends Annotation>, BeanValidationAnnotationHandler> annotationHandlers = new HashMap<>();

    @Override
    public void init(EnhancedRandomParameters parameters) {
        long seed = parameters.getSeed();
        Charset charset = parameters.getCharset();
        annotationHandlers.put(AssertFalse.class, new AssertFalseAnnotationHandler());
        annotationHandlers.put(AssertTrue.class, new AssertTrueAnnotationHandler());
        annotationHandlers.put(Null.class, new NullAnnotationHandler());
        annotationHandlers.put(Future.class, new FutureAnnotationHandler(seed));
        annotationHandlers.put(Past.class, new PastAnnotationHandler(seed));
        annotationHandlers.put(Min.class, new MinMaxAnnotationHandler(seed));
        annotationHandlers.put(Max.class, new MinMaxAnnotationHandler(seed));
        annotationHandlers.put(DecimalMin.class, new DecimaMinMaxAnnotationHandler(seed));
        annotationHandlers.put(DecimalMax.class, new DecimaMinMaxAnnotationHandler(seed));
        annotationHandlers.put(Pattern.class, new PatternAnnotationHandler(seed));
        annotationHandlers.put(Size.class, new SizeAnnotationHandler(seed, charset));
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {
        final Method readMethod = getReadMethod(field).orElse(null);
        for (Map.Entry<Class<? extends Annotation>, BeanValidationAnnotationHandler> entry : annotationHandlers.entrySet()) {
            Class<? extends Annotation> annotation = entry.getKey();
            BeanValidationAnnotationHandler annotationHandler = entry.getValue();
            if (isAnnotationPresent(field, annotation) && annotationHandler != null) {
                return annotationHandler.getRandomizer(field);
            }
        }
        return null;
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> fieldType) {
        return null;
    }
}
