package org.gogil.chat.server.validator;

import java.util.List;

public class MessageValidator {

    private final List<IValidator<String>> textValidators = List.of(
            new NotBlankValidator("Сообщение не может быть пустым")
    );

    public void validate(String text) throws ValidationException {
        for (IValidator<String> validator : textValidators) {
            validator.validate(text);
        }
    }
}
