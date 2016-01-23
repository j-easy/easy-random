package io.github.benas.randombeans;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.MapBean;
import io.github.benas.randombeans.beans.Person;

public class MapPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testMapInterfacesPopulation() throws Exception {
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getSortedMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getSortedMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedSortedMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedSortedMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getNavigableMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getNavigableMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedNavigableMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedNavigableMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedConcurrentMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentMap().values());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentNavigableMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentNavigableMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedConcurrentNavigableMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentNavigableMap().values());
    }

    @Test
    public void testMapClassesPopulation() throws Exception {
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getHashMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getHashMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedHashMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedHashMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getHashtable().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getHashtable().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedHashtable().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedHashtable().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getLinkedHashMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getLinkedHashMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedLinkedHashMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedLinkedHashMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getWeakHashMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getWeakHashMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedWeakHashMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedWeakHashMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getIdentityHashMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getIdentityHashMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedIdentityHashMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedIdentityHashMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getTreeMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getTreeMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedTreeMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedTreeMap().values());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentSkipListMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentSkipListMap().keySet());
        assertIsNotEmptyAndContainsNonZeroIntegers(mapBean.getTypedConcurrentSkipListMap().keySet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentSkipListMap().values());
    }

    private void assertIsNotEmptyAndContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        assertThat(persons).isNotEmpty();
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(final Collection collection) {
        assertThat(collection).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void assertIsNotEmptyAndContainsNonZeroIntegers(final Collection collection) {
        assertThat(collection).isNotEmpty().hasOnlyElementsOfType(Integer.class).doesNotContain(0);
    }
}
