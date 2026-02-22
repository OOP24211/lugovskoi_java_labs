package org.gogil;

import java.util.Comparator;
import java.util.Map;

public class WordFrequencyComparator implements Comparator<Map.Entry<String,Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> firstEntry, Map.Entry<String, Integer> secondEntry) {
            int compareResult = secondEntry.getValue().compareTo(firstEntry.getValue());
            if (compareResult != 0) {
                return compareResult;
            }
            return firstEntry.getKey().compareTo(secondEntry.getKey());
        }
}
