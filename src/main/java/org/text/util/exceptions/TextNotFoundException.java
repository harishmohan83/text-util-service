package org.text.util.exceptions;

import org.text.util.enums.ErrorEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TextNotFoundException extends RuntimeException {
    private ErrorEnum errorEnum;
    private String textId;
    
    public TextNotFoundException(ErrorEnum errorEnum, String message, String textId) {
        super(message);
        this.errorEnum = errorEnum;
        this.textId = textId;
    }
}