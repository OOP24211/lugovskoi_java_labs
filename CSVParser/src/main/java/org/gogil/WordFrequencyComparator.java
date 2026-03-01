package org.gogil;

import java.util.Comparator;
import java.util.Map;

public class WordFrequencyComparator implements Comparator<WordFrequency> {
        @Override
        public int compare(WordFrequency first, WordFrequency second) {
            int compareResult = Integer.compare(second.count(), first.count());
            if (compareResult != 0) {
                return compareResult;
            }
            return first.word().compareTo(second.word());
        }
}
