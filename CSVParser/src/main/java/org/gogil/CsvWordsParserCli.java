package org.gogil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class CsvWordsParserCli {
    private static final Logger log = LoggerFactory.getLogger(CsvWordsParserCli.class);
    private final String inputFile;
    private final String outputFile;

    public CsvWordsParserCli(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() throws IOException {
        Map<String, Integer> mapWordFrequency = WordFrequencyCounter.countWords(inputFile);
        Set<Map.Entry<String, Integer>> sortedWordCounter = WordFrequencySorter.sortWordFrequency(mapWordFrequency, new WordFrequencyComparator());
        CsvWriter.writeCsv(outputFile, sortedWordCounter);
        log.info("CSV создан: {}", outputFile);
    }
}
