package org.gogil.validators;

import org.gogil.exceptions.ValidationException;

public interface IValidator<T> {
    void validate(T value) throws ValidationException;
}
