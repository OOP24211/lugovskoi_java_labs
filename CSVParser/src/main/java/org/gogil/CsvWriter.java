package org.gogil;

import java.io.*;
import java.util.*;

public class CsvWriter {
    public static void writeCsv(String fileName, Set<Map.Entry<String, Integer>> wordFrequencyEntries)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("word, frequency\n");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : wordFrequencyEntries) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }
    }
}
