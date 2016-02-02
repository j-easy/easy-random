package io.github.benas.randombeans.randomizers.registry;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry of user defined randomizers.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(-254)
public class CustomRandomizerRegistry implements RandomizerRegistry {

    private Map<CustomRandomizerDefinition<?, ?>, Randomizer<?>> customRandomizersRegistry = new HashMap<>();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Randomizer<?> getRandomizer(Field field) {
        return customRandomizersRegistry.get(new CustomRandomizerDefinition(field.getDeclaringClass(), field.getType(), field.getName()));
    }

    @Override
    public <T> Randomizer<T> getRandomizer(Class<T> type) {
        return null;
    }

    public <T, F, R> void registerRandomizer(final Class<T> type, final Class<F> fieldType, final String fieldName, final Randomizer<R> randomizer) {
        customRandomizersRegistry.put(new CustomRandomizerDefinition<>(type, fieldType, fieldName), randomizer);
    }

    /**
     * This class defines the target object type and the field (type and name) for which a custom {@link Randomizer} should be used.
     * Custom randomizer definitions are unique according to target class type, field type and name.
     *
     * @param <T> class type
     * @param <F> field type
     * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
     */
    class CustomRandomizerDefinition<T, F> {

        private Class<T> type;

        private Class<F> fieldType;

        private String fieldName;

        CustomRandomizerDefinition(Class<T> type, Class<F> fieldType, String fieldName) {
            this.type = type;
            this.fieldType = fieldType;
            this.fieldName = fieldName;
        }

        /*
         * Getters and setters
         */

        public Class<T> getType() {
            return type;
        }

        public void setType(Class<T> type) {
            this.type = type;
        }

        public Class<F> getFieldType() {
            return fieldType;
        }

        public void setFieldType(Class<F> fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        /*
         * Randomizer definitions are unique according to target class type, field type and name.
         */

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            CustomRandomizerDefinition<?, ?> that = (CustomRandomizerDefinition<?, ?>) o;

            return fieldName.equals(that.fieldName) && fieldType.equals(that.fieldType) && type.equals(that.type);
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + fieldType.hashCode();
            result = 31 * result + fieldName.hashCode();
            return result;
        }
    }
}
