package org.gogil.validators;

import org.gogil.exceptions.SourceFileNotFoundException;
import org.gogil.exceptions.ValidationException;
import java.io.File;

public class FileExistsValidator implements IValidator<String> {
    @Override
    public void validate(String filePath) throws ValidationException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new SourceFileNotFoundException("Файл не найден: " + filePath);
        }
    }
}
