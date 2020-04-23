package org.levelup.streams;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtils {
    //  private CollectionUtils() { }

    public static Collection<String> removeLongStrings(Collection<String> collection, int maxLength) {
        return collection.stream()
                .filter(string -> string.length() < maxLength)
                .collect(Collectors.toList());
    }

}
