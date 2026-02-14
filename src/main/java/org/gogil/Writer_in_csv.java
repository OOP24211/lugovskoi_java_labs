package org.gogil;

import java.io.*;
import java.util.*;

public class Writer_in_csv {
    public static void write_csv(String file_name, Set<Map.Entry<String, Integer>> sorted_word_stats)
            throws IOException {
        try (BufferedWriter buffered_writer = new BufferedWriter(new FileWriter(file_name))) {
            buffered_writer.write("word, frequency\n");
            buffered_writer.newLine();
            for (Map.Entry<String, Integer> sorted_word_entry : sorted_word_stats) {
                buffered_writer.write(
                        sorted_word_entry.getKey() + "," + sorted_word_entry.getValue()
                );
                buffered_writer.newLine();
            }
        }
    }
}