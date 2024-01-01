package com.rodrigo.helpdesk.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 6217932302091090895L;

    private String fieldName;
    private String message;
}
