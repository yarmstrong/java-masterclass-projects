package com.hoken;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Integer> squares = new HashSet<>();
        Set<Integer> cubes = new HashSet<>();

        for (int i = 1; i <= 100; i++) {
            squares.add(i*i);
            cubes.add(i*i*i);
        }

        System.out.println("squares.size(): " + squares.size() + "; cubes.size(): " + cubes.size());

        /* 1. UNION: addAll() */
        Set<Integer> union = new HashSet<>(squares);
        union.addAll(cubes); // combi, no duplicates =>> (set1+set2)size 200 = union (196) + intersection (4)
        System.out.println("union.size(): " + union.size());

        /* 2. INTERSECTION: retainAll() */
        Set<Integer> intersect = new HashSet<>(squares);
        intersect.retainAll(cubes); // retain same entry found on both sets
        System.out.println("intersect.size(): " + intersect.size());

        for (int i : intersect) {
            System.out.println(i + " is the sqrt of " + Math.sqrt(i) + " and cbrt of " + Math.cbrt(i));
        }

        // PUTTING A DUPLICATED ARRAY INTO A SET WILL GUARANTEE THAT SET WILL REMOVE DUPLICATED VALUE
        Set<String> words = new HashSet<>();
        String sentence = "one day in the year of the fox";
        String[] arr = sentence.split(" "); // char[] charArr = sentence.toCharArray();
        words.addAll(Arrays.asList(arr));
        System.out.println("arr.length: " + arr.length + "; words.size(): " + words.size() + "the other duplicate 'to' is removed");

        for (String str : words) {
            System.out.println(str);
        }


        // 3. removeAll(): remove from set1 those that are duplicate in set2 and vice versa also equals to set1-(set1 intersection set2)
        Set<String> nature = new HashSet<>(Arrays.asList("all","nature","is","but","art","unknownst","to","thee"));
        Set<String> divine = new HashSet<>(Arrays.asList("to","err","is","human","to","forgive","divine"));
        Set<String> natureMinusDivine = new HashSet<>(nature);
        natureMinusDivine.removeAll(divine); // 'to' and 'is' are intersection
        System.out.println("nature.size(): " + nature.size() + "; natureMinusDivine.size(): " + natureMinusDivine.size() + "; the intersection 'to' and 'is' removed");

        for (String str : natureMinusDivine) {
            System.out.println(str);
        }

        // 4. containAll(): check if set2 is subset of set1
        if (nature.containsAll(Arrays.asList("all","nature","is"))) {
            System.out.println("nature contains all of (\"all\",\"nature\",\"is\")");
        }

    }
}
