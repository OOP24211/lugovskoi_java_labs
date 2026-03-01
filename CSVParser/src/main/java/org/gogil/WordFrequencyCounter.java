package org.gogil;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFrequencyCounter {
    private static final Pattern wordPattern = Pattern.compile("[a-zA-Zа-яА-ЯёЁ]+(?:[-'][a-zA-Zа-яА-ЯёЁ]+)*");

    public static Map<String, Integer> countWords(BufferedReader reader) throws IOException {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = wordPattern.matcher(line.toLowerCase());
            while (matcher.find()) {
                String word = matcher.group();
                wordFrequencyMap.merge(word, 1, Integer::sum);
            }
        }
        return wordFrequencyMap;
    }
}
