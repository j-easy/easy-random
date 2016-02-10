package io.github.benas.randombeans;

import io.github.benas.randombeans.annotation.Exclude;

import java.lang.reflect.Field;

import static io.github.benas.randombeans.util.ReflectionUtils.isStatic;

/**
 * Component that encapsulates the logic of field exclusion in a given population context.
 * This class implements exclusion rules in the predefined order.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldExclusionChecker {

    /**
     * Given the current population context, should the field be excluded from being populated ?
     * @param field the field to check
     * @param context the current population context
     * @return true if the field should be excluded, false otherwise
     */
    boolean shouldBeExcluded(final Field field, final PopulatorContext context) {
        if (field.isAnnotationPresent(Exclude.class)) {
            return true;
        }
        if (isStatic(field)) {
            return true;
        }
        if (context.getExcludedFields().length == 0) {
            return false;
        }
        String fieldFullName = context.getFieldFullName(field);
        for (String excludedFieldName : context.getExcludedFields()) {
            if (fieldFullName.equalsIgnoreCase(excludedFieldName)) {
                return true;
            }
        }
        return false;
    }
}
