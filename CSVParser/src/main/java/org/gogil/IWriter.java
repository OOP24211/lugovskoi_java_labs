package org.gogil;

import java.io.IOException;
import java.util.List;

public interface IWriter {
    void write(String fileName, List<List<String>> rows) throws IOException;
}
