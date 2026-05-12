package org.gogil.chat.server.validator;

import java.util.List;

public class RoomValidator {

    private final List<IValidator<String>> nameValidators = List.of(
            new NotBlankValidator("Название комнаты не может быть пустым")
    );

    public void validate(String roomName) throws ValidationException {
        for (IValidator<String> validator : nameValidators) {
            validator.validate(roomName);
        }
    }
}
