package org.levelup.streams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


class CollectionUtilsTest {
    //test<Method name>_when<input parameters>_then<Results>
    // testRemoveLongStrings_whenCollectionIsValid_thenFilterCollection()
    // testRemoveLongStrings_validCollection_returnFilteredCollection()
    @Test
    public void testRemoveLongStrings_whenEmptyCollection_thenEmptyCollectionCopy() {
        Collection<String> emptyCollection = new ArrayList<>();
        Collection<String> result = CollectionUtils.removeLongStrings(emptyCollection, 10);
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertNotSame(emptyCollection, result);
    }

    @Test
    public void testRemoveLongStrings_whenCollectionIsNull_throwException() {
        Assertions.assertThrows(NullPointerException.class, () -> CollectionUtils.removeLongStrings(null, 10));
    }

    @Test
    public void testRemoveLongStrings_whenCollectionIsNValid_thenFilterCollection() {
        Collection<String> originalCollection = new ArrayList<>(Arrays.asList("String1","String2","LongString"));
        Collection<String> result = CollectionUtils.removeLongStrings(originalCollection, 10);
        Assertions.assertEquals(3,originalCollection.size());
        Assertions.assertEquals(2,result.size());
        Assertions.assertNotEquals(originalCollection.size(), result.size());

        boolean isAllStringsLengthLessThan10 = result.stream()
                .allMatch(string -> string.length()<=10);
        Assertions.assertTrue(isAllStringsLengthLessThan10);

    }
}