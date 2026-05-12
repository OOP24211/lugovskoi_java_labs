package org.gogil.chat.server.validator;

public class NotBlankValidator implements IValidator<String> {
    private final String errorMessage;

    public NotBlankValidator(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException(errorMessage);
        }
    }
}
