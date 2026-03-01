package org.gogil;

import java.util.*;

public class WordFrequencySorter {
    public static List<WordFrequency> sortWordFrequency(
            Map<String, Integer> mapWordFrequency,
            Comparator<WordFrequency> comparator) {
        List<WordFrequency> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapWordFrequency.entrySet()) {
            entries.add(new WordFrequency(entry.getKey(), entry.getValue()));
        }
        entries.sort(comparator);
        return entries;
    }
}
