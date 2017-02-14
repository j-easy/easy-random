package io.github.benas.randombeans.visibility;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.beans.Human;
import org.junit.Test;

import java.util.function.Supplier;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.junit.Assert.assertEquals;

public class VisibilityTest {
    @Test
    public void canPassSupplierLambdaFromOtherPackage() {
        EnhancedRandomBuilder builder = aNewEnhancedRandomBuilder();
        FieldDefinition field = FieldDefinitionBuilder.field().named("name").ofType(String.class).inClass(Human.class).get();
        builder.randomize(field, (Supplier) () -> "test");
        EnhancedRandom random = builder.build();
        Human human = random.nextObject(Human.class);
        assertEquals("test", human.getName());
    }
}
