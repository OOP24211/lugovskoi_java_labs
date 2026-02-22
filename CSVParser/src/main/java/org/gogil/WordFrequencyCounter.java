package org.gogil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequencyCounter {
    private static final Pattern wordPattern = Pattern.compile("[a-zA-Zа-яА-ЯёЁ]+(?:[-'][a-zA-Zа-яА-ЯёЁ]+)*");

    public static Map<String, Integer> countWords(String fileName) throws IOException {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = wordPattern.matcher(line.toLowerCase());
                while (matcher.find()) {
                    String word = matcher.group();
                    wordFrequencyMap.merge(word, 1, Integer::sum);
                }
            }
        }
        return wordFrequencyMap;
    }
}
