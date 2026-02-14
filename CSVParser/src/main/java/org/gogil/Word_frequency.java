package org.gogil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word_frequency {
    private static final Pattern word_pattern = Pattern.compile("[a-zA-Zа-яА-ЯёЁ]+(?:[-'][a-zA-Zа-яА-ЯёЁ]+)*");

    public static Map<String, Integer> count_words(String file_name) throws IOException {
        Map<String, Integer> map_word_frequency = new HashMap<>();
        try (BufferedReader buffered_reader = new BufferedReader(new FileReader(file_name))) {
            String line;
            while ((line = buffered_reader.readLine()) != null) {
                Matcher word_find = word_pattern.matcher(line.toLowerCase());
                while (word_find.find()) {
                    String found_word = word_find.group();
                    map_word_frequency.merge(found_word, 1, Integer::sum);
                }
            }
        }
        return map_word_frequency;
    }
}