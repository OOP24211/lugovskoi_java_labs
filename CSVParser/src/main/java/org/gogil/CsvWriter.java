package org.gogil;

import java.io.*;
import java.util.List;

public class CsvWriter implements IWriter {
    @Override
    public void write(String fileName, List<List<String>> rows) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("word,frequency");
            writer.newLine();
            for (List<String> row : rows) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
}
