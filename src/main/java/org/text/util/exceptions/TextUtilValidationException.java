package org.text.util.exceptions;

import org.text.util.enums.ErrorEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TextUtilValidationException extends RuntimeException {
    private ErrorEnum errorEnum;
    private String arbitraryText;
    private String textId;
    
    public TextUtilValidationException(ErrorEnum errorEnum, String message) {
        this(errorEnum, message, null, null);
    }
    
    public TextUtilValidationException(ErrorEnum errorEnum, String arbitraryText, String textId) {
        this(errorEnum, null, arbitraryText, null);
    }
    
    public TextUtilValidationException(ErrorEnum errorEnum, String message, String arbitraryText, String textId) {
        super(message);
        this.errorEnum = errorEnum;
        this.arbitraryText = arbitraryText;
        this.textId = textId;
    }
}