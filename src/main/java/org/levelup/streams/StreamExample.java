package org.levelup.streams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        Collection<Integer> integers = new ArrayList<>();
        integers.add(52);
        integers.add(7587);
        integers.add(1);
        integers.add(23);
        integers.add(8550);
        integers.add(36);
        integers.add(58);
        integers.add(158);

        List<Integer> sortedCollection = new ArrayList<>(integers);
        Collections.sort(sortedCollection);

        List<String> integersAsStrings = new ArrayList<>(integers.size());
        for (Integer integer : integers) {
            integersAsStrings.add(String.valueOf(integer));

        }


        Collection<Integer> sorted = integers.stream().sorted().collect(Collectors.toList());

        Collection<String> strings = integers
                .stream()
                .filter(integer -> integer>9 && integer <10000)
                .map(integer -> String.valueOf(integer))
                .filter(string -> string.length()<=3)
                .collect(Collectors.toList());

strings.stream().allMatch(string -> string.length()==2);

        System.out.println(sorted);
        System.out.println(strings);
    }
}


