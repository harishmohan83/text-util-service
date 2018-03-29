package org.text.util.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.text.util.binding.v1.Error;
import org.text.util.binding.v1.TextResponse;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.JsonPlaceHolderException;
import org.text.util.exceptions.TextNotFoundException;
import org.text.util.exceptions.TextUtilValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * The {@link TextUtilControllerAdvise} class is the central exception class for
 * all the exceptions in the text util service.
 */
@ControllerAdvice
@Slf4j
public class TextUtilControllerAdvise {

    /**
     * This method handles the {@link TextNotFoundException} exceptions.
     *
     * @param e
     *            the exception
     * @return the textResponse
     */
    @ExceptionHandler(TextNotFoundException.class)
    @ResponseBody
    public ResponseEntity<TextResponse> handleTextNotFoundExceptions(TextNotFoundException e) {
        TextResponse textResponse = new TextResponse();
        textResponse.setTextId(e.getTextId());
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(e.getErrorEnum().getCode());
        error.setDescription(e.getErrorEnum().getDescription());
        errors.add(error);
        textResponse.setErrors(errors);
        return new ResponseEntity<>(textResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the {@link TextUtilValidationException} exceptions.
     *
     * @param e
     *            the exception
     * @return the textResponse
     */
    @ExceptionHandler(TextUtilValidationException.class)
    @ResponseBody
    public ResponseEntity<TextResponse> handleTextUtilValidationExceptions(TextUtilValidationException e) {
        TextResponse textResponse = new TextResponse();
        if (StringUtils.isNotBlank(e.getArbitraryText())) {
            textResponse.setArbitraryText(e.getArbitraryText());
        }
        if (StringUtils.isNotBlank(e.getTextId())) {
            textResponse.setTextId(e.getTextId());
        }
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(e.getErrorEnum().getCode());
        error.setDescription(e.getErrorEnum().getDescription());
        errors.add(error);
        textResponse.setErrors(errors);
        return new ResponseEntity<>(textResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the {@link JsonPlaceHolderException} exceptions.
     *
     * @param e
     *            the exception
     * @return the string
     */
    @ExceptionHandler(JsonPlaceHolderException.class)
    @ResponseBody
    public ResponseEntity<String> handleJsonPlaceHolderExceptions(JsonPlaceHolderException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method handles all the unhandled exceptions in the service
     *
     * @param e
     *            the exception
     * @return the textResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<TextResponse> handleExceptions(Exception e) {
        log.error("Unexpected error occured", e);
        TextResponse textResponse = new TextResponse();
        List<Error> errors = new ArrayList<>();
        Error error = new Error();
        error.setCode(ErrorEnum.UNKNOWN_ERROR.getCode());
        error.setDescription(ErrorEnum.UNKNOWN_ERROR.getDescription());
        errors.add(error);
        textResponse.setErrors(errors);
        return new ResponseEntity<>(textResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
