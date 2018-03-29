package org.text.util.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.exceptions.JsonPlaceHolderException;
import org.text.util.services.JsonPlaceHolderService;

public class JsonPlaceHolderControllerTest {

    @Mock
    private JsonPlaceHolderService jsonPlaceHolderService;

    @InjectMocks
    private JsonPlaceHolderController jsonPlaceHolderController;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fecthJsonPlaceHolderDataSuccessfulTest() {
        doReturn("json").when(jsonPlaceHolderService).service(any());
        ResponseEntity<String> response = jsonPlaceHolderController.fetchjsonPlaceHolderData();
        verify(jsonPlaceHolderService, times(1)).service(any());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "json");
    }

    @Test(expectedExceptions = { JsonPlaceHolderException.class })
    public void fecthJsonPlaceHolderDataErrorTest() {
        doThrow(new JsonPlaceHolderException("internal error")).when(jsonPlaceHolderService).service(any());
        ResponseEntity<String> response = jsonPlaceHolderController.fetchjsonPlaceHolderData();
        verify(jsonPlaceHolderService, times(1)).service(any());
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody(), "internal error");
    }
    
}
