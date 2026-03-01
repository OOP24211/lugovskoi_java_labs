package org.gogil;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class WordFrequencyAdapter {
    public List<List<String>> adapt(List<WordFrequency> entries) {
        List<List<String>> rows = new ArrayList<>();
        for (WordFrequency entry : entries) {
            rows.add(List.of(entry.word(), String.valueOf(entry.count())));
        }
        return rows;
    }
}
