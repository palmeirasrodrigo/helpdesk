package com.rodrigo.helpdesk.resources.exceptions;

import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class ValidationsError extends StandardError {

    @Serial
    private static final long serialVersionUID = 4823134128120640955L;

    private final List<FieldMessage> errors = new ArrayList<>();

    ValidationsError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
