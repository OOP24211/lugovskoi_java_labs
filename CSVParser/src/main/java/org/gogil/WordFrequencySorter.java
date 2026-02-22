package org.gogil;

import java.util.*;

public class WordFrequencySorter {
    public static Set<Map.Entry<String, Integer>> sortWordFrequency(Map<String, Integer> mapWordFrequency, Comparator<Map.Entry<String, Integer>> comparator) {
        Set<Map.Entry<String, Integer>> entrySet = mapWordFrequency.entrySet();
        Set<Map.Entry<String, Integer>> sortedEntries = new TreeSet<>(comparator);
        sortedEntries.addAll(entrySet);
        return sortedEntries;
    }
}
