package org.gogil.validators;

import org.gogil.exceptions.SourceFileNotFoundException;
import org.gogil.exceptions.ValidationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileExistsValidator implements IValidator<String> {
    @Override
    public void validate(String filePath) throws ValidationException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new SourceFileNotFoundException("Файл не найден: " + filePath);
        }
    }
}
