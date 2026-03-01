package org.gogil.validators;

import org.gogil.exceptions.InvalidFileExtensionException;
import org.gogil.exceptions.ValidationException;

public class FileExtensionValidator implements IValidator<String> {
    private final String expectedExtension;

    public FileExtensionValidator(String expectedExtension) {
        this.expectedExtension = expectedExtension;
    }
    @Override
    public void validate(String filePath) throws ValidationException {
        if (!filePath.endsWith(expectedExtension)) {
            throw new InvalidFileExtensionException("Неправильное расширение файла: ожидалось " + expectedExtension + ", получено " + filePath);
        }
    }
}
