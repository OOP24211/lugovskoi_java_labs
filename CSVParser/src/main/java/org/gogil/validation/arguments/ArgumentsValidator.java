package org.gogil.arguments;

import org.gogil.exceptions.MissingArgumentException;
import org.gogil.validators.FileExistsValidator;
import org.gogil.validators.FileExtensionValidator;
import org.gogil.validators.IValidator;

import java.util.List;

public class ArgumentsValidator {
    private final List<IValidator<String>> inputValidators = List.of(
            new FileExistsValidator(),
            new FileExtensionValidator(".txt")
    );

    private final List<IValidator<String>> outputValidators = List.of(
            new FileExtensionValidator(".csv")
    );

    public void validate(String[] args) {
        if (args.length != 2) {
            throw new MissingArgumentException("Ожидалось 2 аргумента: input.txt output.csv, получено: " + args.length);
        }
        String inputFile = args[0];
        String outputFile = args[1];
        for (IValidator<String> validator : inputValidators) {
            validator.validate(inputFile);
        }
        for (IValidator<String> validator : outputValidators) {
            validator.validate(outputFile);
        }
    }
}
