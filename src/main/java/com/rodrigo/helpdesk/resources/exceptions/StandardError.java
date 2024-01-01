package com.rodrigo.helpdesk.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 2212481895593896361L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
