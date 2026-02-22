package org.gogil.arguments;

import org.gogil.exceptions.MissingArgumentException;
import org.gogil.validators.FileExistsValidator;
import org.gogil.validators.FileExtensionValidator;

public class ArgumentsValidator {
    private final FileExistsValidator fileExistsValidator = new FileExistsValidator();
    private final FileExtensionValidator txtExtensionValidator = new FileExtensionValidator(".txt");
    private final FileExtensionValidator csvExtensionValidator = new FileExtensionValidator(".csv");

    public void validate(String[] args) {
        if (args.length != 2) {
            throw new MissingArgumentException("Ожидалось 2 аргумента: input.txt output.csv, получено: " + args.length);
        }
        String inputFile = args[0];
        String outputFile = args[1];
        fileExistsValidator.validate(inputFile);
        txtExtensionValidator.validate(inputFile);
        csvExtensionValidator.validate(outputFile);
    }
}
