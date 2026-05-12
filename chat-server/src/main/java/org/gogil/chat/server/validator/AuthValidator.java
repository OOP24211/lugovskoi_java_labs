package org.gogil.chat.server.validator;

import java.util.List;

public class AuthValidator {

    private final List<IValidator<String>> nameValidators = List.of(
            new NotBlankValidator("Имя не может быть пустым")
    );

    private final List<IValidator<String>> passwordValidators = List.of(
            new NotBlankValidator("Пароль не может быть пустым")
    );

    public void validate(String userName, String password) throws ValidationException {
        for (IValidator<String> validator : nameValidators) {
            validator.validate(userName);
        }
        for (IValidator<String> validator : passwordValidators) {
            validator.validate(password);
        }
    }
}
