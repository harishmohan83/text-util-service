package org.text.util.enums;

public enum ErrorEnum {
    
    TEXT_NULL_OR_EMPTY(1001, "Text in the request is null or empty"),
    TEXT_NOT_VALID(1002, "Unacceptable characters in input text {}"),
    TEXT_ID_NOT_VALID(1003, "Text Id {} in the request is not valid"),
    TEXT_ID_NULL_OR_EMPTY(1004, "Text Id in the request is null or empty"),
    TEXT_ID_NOT_FOUND(2001, "Text Id {} was not found"),
    UNKNOWN_ERROR(5001, "UnExpected error occured");
    
    private Integer errorCode;
    private String description;
    
    ErrorEnum(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }
    
    public Integer getCode() {
        return this.errorCode;
    }
    
    public String getDescription() {
        return this.description;
    }

}
