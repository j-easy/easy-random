package io.github.benas.randombeans;

/**
 * Builder for {@link ExclusionDefinition}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ExclusionDefinitionBuilder {

    private String name;

    private Class<?> type;

    private Class<?> clazz;

    public static ExclusionDefinitionBuilder field() {
        return new ExclusionDefinitionBuilder();
    }

    /**
     * Specify the field name.
     * @param name the field name
     * @return the configured {@link ExclusionDefinitionBuilder}
     */
    public ExclusionDefinitionBuilder named(String name) {
        this.name = name;
        return this;
    }

    /**
     * Specify the field type.
     * @param type the field type
     * @return the configured {@link ExclusionDefinitionBuilder}
     */
    public ExclusionDefinitionBuilder ofType(Class<?> type) {
        this.type = type;
        return this;
    }

    /**
     * Specify the class type.
     * @param clazz the class type
     * @return the configured {@link ExclusionDefinitionBuilder}
     */
    public ExclusionDefinitionBuilder inClass(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ExclusionDefinition get() {
        return new ExclusionDefinition(name, type, clazz);
    }
}
