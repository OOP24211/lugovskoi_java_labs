package org.gogil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gogil.arguments.ArgumentsValidator;
import org.gogil.exceptions.ValidationException;
import java.io.IOException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            new ArgumentsValidator().validate(args);
            new CsvWordsParserCli(args[0], args[1]).run();
        } catch (ValidationException exception) {
            log.error("Ошибка валидации: {}", exception.getMessage());
        } catch (IOException exception) {
            log.error("Ошибка: {}", exception.getMessage());
        }
    }
}
