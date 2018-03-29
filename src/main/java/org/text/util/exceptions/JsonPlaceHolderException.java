package org.text.util.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JsonPlaceHolderException extends RuntimeException {

    public JsonPlaceHolderException(String message) {
        super(message);
    }
}