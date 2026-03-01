package org.gogil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.List;
public class CsvWordsParserCli {
    private static final Logger log = LoggerFactory.getLogger(CsvWordsParserCli.class);
    private final String inputFile;
    private final String outputFile;

    public CsvWordsParserCli(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            Map<String, Integer> mapWordFrequency = WordFrequencyCounter.countWords(reader);
            List<WordFrequency> sortedWords = WordFrequencySorter.sortWordFrequency(mapWordFrequency, new WordFrequencyComparator());
            List<List<String>> rows = new WordFrequencyAdapter().adapt(sortedWords);
            new CsvWriter().write(outputFile, rows);
            log.info("CSV создан: {}", outputFile);
        }
    }
}
