package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testCollectionInterfacesPopulation() throws Exception {
        final CollectionInterfacesBean collectionsBean = populator.populateBean(CollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getCollection());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getList());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSet());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSortedSet());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getNavigableSet());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getQueue());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getDeque());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getMap().keySet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSortedMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSortedMap().keySet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getNavigableMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getNavigableMap().keySet());
    }

    @Test
    public void testCollectionClassesPopulation() throws Exception {
        final CollectionClassesBean collectionsBean = populator.populateBean(CollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getVector());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayList());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedList());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getHashSet());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getTreeSet());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentSkipListSet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getHashMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getHashMap().keySet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getTreeMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getTreeMap().keySet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentSkipListMap().values());
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentSkipListMap().keySet());

        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayDeque());
    }

    @Test
    public void testTypedCollectionInterfacesPopulation() throws Exception {
        final TypedCollectionInterfacesBean collectionsBean = populator.populateBean(TypedCollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();
        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerCollection());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonCollection());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerSet());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonSet());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerSortedSet());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonSortedSet());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerNavigableSet());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonNavigableSet());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerList());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonList());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonQueue());

        assertIsNotEmptyAndContainsNonZeroIntegers(collectionsBean.getIntegerDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonDeque());

        assertThat(collectionsBean.getIntegerMap()).isNotEmpty().doesNotContainKey(null).doesNotContainKey("").doesNotContainValue(0);

        Map<String, SocialPerson> socialPersonMap = collectionsBean.getSocialPersonMap();
        assertThat(socialPersonMap).isNotEmpty().doesNotContainKey(null).doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonMap.values());

        assertThat(collectionsBean.getIntegerSortedMap()).isNotEmpty().doesNotContainKey("").doesNotContainValue(0);

        SortedMap<String, SocialPerson> socialPersonSortedMap = collectionsBean.getSocialPersonSortedMap();
        assertThat(socialPersonSortedMap).isNotEmpty().doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonSortedMap.values());

        assertThat(collectionsBean.getIntegerNavigableMap()).isNotEmpty().doesNotContainKey("").doesNotContainValue(0);

        assertThat(collectionsBean.getSocialPersonNavigableMap()).isNotEmpty().doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonNavigableMap().values());
    }

    @Test
    public void testTypedCollectionClassesPopulation() throws Exception {
        final TypedCollectionClassesBean collectionsBean = populator.populateBean(TypedCollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getStringArray()).isNotEmpty().doesNotContain(null, "");
        assertThat(collectionsBean.getIntegerArray()).isNotEmpty().doesNotContain(0);

        assertThat(collectionsBean.getIntegerVector()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonVector());

        assertThat(collectionsBean.getIntegerArrayList()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonArrayList());

        assertThat(collectionsBean.getIntegerLinkedList()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonLinkedList());

        assertThat(collectionsBean.getIntegerHashSet()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonHashSet());

        assertThat(collectionsBean.getIntegerTreeSet()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonTreeSet());

        assertThat(collectionsBean.getIntegerConcurrentSkipListSet()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonConcurrentSkipListSet());

        assertThat(collectionsBean.getIntegerHashMap()).isNotEmpty()
                .doesNotContainKey(null).doesNotContainKey("")
                .doesNotContainValue(0);

        HashMap<String, SocialPerson> socialPersonHashMap = collectionsBean.getSocialPersonHashMap();
        assertThat(socialPersonHashMap).isNotEmpty().doesNotContainKey(null).doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonHashMap.values());

        assertThat(collectionsBean.getIntegerTreeMap()).isNotEmpty().doesNotContainKey("").doesNotContainValue(0);

        TreeMap<String, SocialPerson> socialPersonTreeMap = collectionsBean.getSocialPersonTreeMap();
        assertThat(socialPersonTreeMap).isNotEmpty().doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonTreeMap.values());

        assertThat(collectionsBean.getIntegerConcurrentSkipListMap()).isNotEmpty().doesNotContainKey("").doesNotContainValue(0);

        ConcurrentSkipListMap<String, SocialPerson> socialPersonConcurrentSkipListMap = collectionsBean.getSocialPersonConcurrentSkipListMap();
        assertThat(socialPersonConcurrentSkipListMap).isNotEmpty().doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonConcurrentSkipListMap.values());

        assertThat(collectionsBean.getIntegerArrayDeque()).isNotEmpty().doesNotContain(0);
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonArrayDeque());
    }

    private void assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(Collection<SocialPerson> persons) {
        assertThat(persons).isNotEmpty();
        for (SocialPerson socialPerson : persons) {
            assertThat(socialPerson).isNotNull();
            assertThat(socialPerson.getAddress().getCity()).isNotEmpty();
            assertThat(socialPerson.getAddress().getZipCode()).isNotEmpty();
            assertThat(socialPerson.getName()).isNotEmpty();
            assertThat(socialPerson.getFriends()).isNotEmpty();
            Person friend = socialPerson.getFriends().iterator().next();
            assertThat(friend.getName()).isNotEmpty();
            assertThat(friend.getNicknames()).isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotEmpty();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(final Collection collection) {
        assertThat(collection).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assertIsNotEmptyAndContainsNonZeroIntegers(final Collection collection) {
        assertThat(collection).isNotEmpty().hasOnlyElementsOfType(Integer.class).doesNotContain(0);
    }

}
