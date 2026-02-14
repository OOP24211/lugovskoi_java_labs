package org.gogil;

import java.util.*;

public class Word_counter {
    public static Set<Map.Entry<String, Integer>> sort_word_frequency(Map<String, Integer> map_word_frequency) {
        Set<Map.Entry<String, Integer>> unsorted_word_frequency = map_word_frequency.entrySet();
        Set<Map.Entry<String, Integer>> sorted_word_frequency_pairs =
                new TreeSet<>(
                        (first_pair, second_pair) -> {
                            int compare_result = second_pair.getValue().compareTo(first_pair.getValue());
                            if (compare_result != 0) {
                                return compare_result;
                            }
                            return first_pair.getKey().compareTo(second_pair.getKey());
                        }
                );
        sorted_word_frequency_pairs.addAll(unsorted_word_frequency);
        return sorted_word_frequency_pairs;
    }
}