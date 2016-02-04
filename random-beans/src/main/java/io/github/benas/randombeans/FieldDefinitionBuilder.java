package io.github.benas.randombeans;

/**
 * Builder for {@link FieldDefinition}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldDefinitionBuilder {

    private String name;

    private Class<?> type;

    private Class<?> clazz;

    /**
     * Create a new {@link FieldDefinitionBuilder}.
     *
     * @return a new {@link FieldDefinitionBuilder}
     */
    public static FieldDefinitionBuilder field() {
        return new FieldDefinitionBuilder();
    }

    /**
     * Specify the field name.
     *
     * @param name the field name
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder named(String name) {
        this.name = name;
        return this;
    }

    /**
     * Specify the field type.
     *
     * @param type the field type
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder ofType(Class<?> type) {
        this.type = type;
        return this;
    }

    /**
     * Specify the class type.
     *
     * @param clazz the class type
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder inClass(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * Create a new {@link FieldDefinition}.
     *
     * @return a new {@link FieldDefinition}
     */
    public FieldDefinition get() {
        checkArguments();
        return new FieldDefinition(name, type, clazz);
    }

    private void checkArguments() {
        if (name == null || type == null || clazz == null) {
            throw new IllegalArgumentException("Arguments 'name', 'type' and 'class' are required");
        }
    }

}
