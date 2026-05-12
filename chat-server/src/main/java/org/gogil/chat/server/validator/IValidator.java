package org.gogil.chat.server.validator;

public interface IValidator<T> {
    void validate(T value) throws ValidationException;
}
