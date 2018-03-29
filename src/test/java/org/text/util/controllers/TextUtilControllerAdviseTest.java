package org.text.util.controllers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.binding.v1.TextResponse;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.JsonPlaceHolderException;
import org.text.util.exceptions.TextNotFoundException;
import org.text.util.exceptions.TextUtilValidationException;

public class TextUtilControllerAdviseTest {

    @InjectMocks
    private TextUtilControllerAdvise textUtilControllerAdvise;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleTextNotFoundExceptionsTest() {
        TextNotFoundException e = new TextNotFoundException(ErrorEnum.TEXT_ID_NOT_FOUND, "not found",
                "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleTextNotFoundExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.TEXT_ID_NOT_FOUND.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                ErrorEnum.TEXT_ID_NOT_FOUND.getDescription());
    }

    @Test
    public void handleTextUtilvalidationExceptionsTest() {
        TextUtilValidationException e = new TextUtilValidationException(ErrorEnum.TEXT_ID_NOT_VALID, "", "not valid",
                "091c62c5-ea3e-4aaa-9ada-&&");
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleTextUtilValidationExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-&&");
        assertEquals(response.getBody().getArbitraryText(), "not valid");
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.TEXT_ID_NOT_VALID.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                ErrorEnum.TEXT_ID_NOT_VALID.getDescription());
    }

    @Test
    public void emptyTextIdTextUtilvalidationExceptionsTest() {
        TextUtilValidationException e = new TextUtilValidationException(ErrorEnum.TEXT_ID_NULL_OR_EMPTY, null,
                "not valid", null);
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleTextUtilValidationExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody().getTextId());
        assertEquals(response.getBody().getArbitraryText(), "not valid");
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.TEXT_ID_NULL_OR_EMPTY.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                ErrorEnum.TEXT_ID_NULL_OR_EMPTY.getDescription());
    }

    @Test
    public void emptyArbitraryTextTextUtilvalidationExceptionsTest() {
        TextUtilValidationException e = new TextUtilValidationException(ErrorEnum.TEXT_NULL_OR_EMPTY, null, "",
                "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleTextUtilValidationExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertNull(response.getBody().getArbitraryText());
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.TEXT_NULL_OR_EMPTY.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                ErrorEnum.TEXT_NULL_OR_EMPTY.getDescription());
    }

    @Test
    public void emptyArbitraryTextAndTextIdTextUtilvalidationExceptionsTest() {
        TextUtilValidationException e = new TextUtilValidationException(ErrorEnum.TEXT_NULL_OR_EMPTY, null, null, "");
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleTextUtilValidationExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody().getArbitraryText());
        assertNull(response.getBody().getTextId());
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.TEXT_NULL_OR_EMPTY.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                ErrorEnum.TEXT_NULL_OR_EMPTY.getDescription());
    }

    @Test
    public void handleJsonPlaceHolderExceptionsTest() {
        JsonPlaceHolderException e = new JsonPlaceHolderException("internal error");
        ResponseEntity<String> response = textUtilControllerAdvise.handleJsonPlaceHolderExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody(), "internal error");
    }

    @Test
    public void handleGeneralExceptionsTest() {
        Exception e = new Exception("General Exception");
        ResponseEntity<TextResponse> response = textUtilControllerAdvise.handleExceptions(e);
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getErrors().get(0).getCode(), ErrorEnum.UNKNOWN_ERROR.getCode());
        assertEquals(response.getBody().getErrors().get(0).getDescription(), ErrorEnum.UNKNOWN_ERROR.getDescription());
    }

}
