package org.gogil;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String input_file = "input.txt";
        String output_file = "output.csv";
        try {
            Map<String, Integer> map_word_frequency = Word_frequency.count_words(input_file);
            Set<Map.Entry<String, Integer>> sorted_word_counter = Word_counter.sort_word_frequency(map_word_frequency);
            Writer_in_csv.write_csv(output_file, sorted_word_counter);
            System.out.println("CSV created: " + output_file);
        }
        catch (IOException exception) {
            System.err.println("Error: " + exception.getMessage());
        }
    }
}
