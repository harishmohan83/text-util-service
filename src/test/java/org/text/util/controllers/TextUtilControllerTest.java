package org.text.util.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.binding.v1.Error;
import org.text.util.binding.v1.TextCreateRequest;
import org.text.util.binding.v1.TextDeleteRequest;
import org.text.util.binding.v1.TextResponse;
import org.text.util.controllers.TextUtilController;
import org.text.util.domain.TextDomain;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.TextUtilValidationException;
import org.text.util.services.CreateTextService;
import org.text.util.services.DeleteTextService;
import org.text.util.services.ReadTextService;
import org.text.util.transformers.TextCreateRequestTransformer;
import org.text.util.transformers.TextDeleteRequestTransformer;
import org.text.util.transformers.TextReadRequestTransformer;
import org.text.util.transformers.TextResponseTransformer;

public class TextUtilControllerTest {

    @Mock
    private TextCreateRequestTransformer textCreateRequestTransformer;

    @Mock
    private TextReadRequestTransformer textReadRequestTransformer;

    @Mock
    private TextDeleteRequestTransformer textDeleteRequestTransformer;

    @Mock
    private TextResponseTransformer textResponseTransformer;

    @Mock
    private CreateTextService createTextService;

    @Mock
    private ReadTextService readTextService;

    @Mock
    private DeleteTextService deleteTextService;

    @Mock
    private TextCreateRequest textCreateRequest;

    @Mock
    private TextDeleteRequest textDeleteRequest;

    @InjectMocks
    private TextUtilController textUtilController;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTextSuccessfulTest() {
        doReturn(createTextDomain(null, "hello")).when(textCreateRequestTransformer).transform(any());
        doReturn(createTextDomain(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"), "hello"))
                .when(createTextService).service(any());
        doReturn(createTextResponse("091c62c5-ea3e-4aaa-9ada-069daef6a803", "hello", null))
                .when(textResponseTransformer).transform(any());
        ResponseEntity<TextResponse> response = textUtilController.createText(textCreateRequest);
        verify(textCreateRequestTransformer, times(1)).transform(any());
        verify(createTextService, times(1)).service(any());
        verify(textResponseTransformer, times(1)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertEquals(response.getBody().getArbitraryText(), "hello");
    }

    @Test(expectedExceptions = { TextUtilValidationException.class })
    public void emptyInputTextOnCreateTest() {
        doThrow(new TextUtilValidationException(ErrorEnum.TEXT_NULL_OR_EMPTY, "")).when(textCreateRequestTransformer)
                .transform(any());
        ResponseEntity<TextResponse> response = textUtilController.createText(textCreateRequest);
        verify(textCreateRequestTransformer, times(1)).transform(any());
        verify(createTextService, times(0)).service(any());
        verify(textResponseTransformer, times(0)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getErrors().get(0).getCode(), new Integer(1001));
        assertEquals(response.getBody().getErrors().get(0).getDescription(), "Text in the request is null or empty");
    }

    @Test(expectedExceptions = { TextUtilValidationException.class })
    public void inValidCharactersInInputTextOnCreateTest() {
        doThrow(new TextUtilValidationException(ErrorEnum.TEXT_NOT_VALID, "")).when(textCreateRequestTransformer)
                .transform(any());
        ResponseEntity<TextResponse> response = textUtilController.createText(textCreateRequest);
        verify(textCreateRequestTransformer, times(1)).transform(any());
        verify(createTextService, times(0)).service(any());
        verify(textResponseTransformer, times(0)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getErrors().get(0).getCode(), new Integer(1001));
        assertEquals(response.getBody().getErrors().get(0).getDescription(),
                "Unacceptable characters in input text {}");
    }

    @Test
    public void readTextSuccessfulTest() {
        doReturn(createTextDomain(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"), "hello"))
                .when(readTextService).service(any());
        doReturn(createTextResponse("091c62c5-ea3e-4aaa-9ada-069daef6a803", "hello", null))
                .when(textResponseTransformer).transform(any());
        ResponseEntity<TextResponse> response = textUtilController.readText("091c62c5-ea3e-4aaa-9ada-069daef6a803");
        verify(textReadRequestTransformer, times(1)).transform(any());
        verify(readTextService, times(1)).service(any());
        verify(textResponseTransformer, times(1)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertEquals(response.getBody().getArbitraryText(), "hello");
    }

    @Test(expectedExceptions = { TextUtilValidationException.class })
    public void inValidInputTextIdOnrReadTest() {
        doThrow(new TextUtilValidationException(ErrorEnum.TEXT_ID_NOT_VALID, "")).when(textReadRequestTransformer)
                .transform(any());
        ResponseEntity<TextResponse> response = textUtilController.readText("091c62c5-ea3e-4&&$");
        verify(textReadRequestTransformer, times(1)).transform(any());
        verify(readTextService, times(0)).service(any());
        verify(textResponseTransformer, times(0)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4&&$");
        assertEquals(response.getBody().getErrors().get(0).getCode(), new Integer(1003));
        assertEquals(response.getBody().getErrors().get(0).getDescription(), "Text Id {} in the request is not valid");
    }

    @Test
    public void textDeleteSuccessfulTest() {
        TextDomain textDomain = createTextDomain(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"), null);
        doReturn(textDomain).when(textDeleteRequestTransformer).transform(any());
        
        doReturn(textDomain).when(deleteTextService).service(any());
        doReturn(createTextResponse("091c62c5-ea3e-4aaa-9ada-069daef6a803", null, null)).when(textResponseTransformer)
                .transform(any());
        ResponseEntity<TextResponse> response = textUtilController.deleteText(textDeleteRequest);
        verify(textDeleteRequestTransformer, times(1)).transform(textDeleteRequest);
        verify(deleteTextService, times(1)).service(textDomain);
        verify(textResponseTransformer, times(1)).transform(textDomain);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
    }

    @Test(expectedExceptions = { TextUtilValidationException.class })
    public void inValidInputTextIdOnDeleteTest() {
        doThrow(new TextUtilValidationException(ErrorEnum.TEXT_ID_NOT_VALID, "")).when(textDeleteRequestTransformer)
                .transform(any());
        ResponseEntity<TextResponse> response = textUtilController.deleteText(any());
        verify(textDeleteRequestTransformer, times(1)).transform(any());
        verify(deleteTextService, times(0)).service(any());
        verify(textResponseTransformer, times(0)).transform(any());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody().getArbitraryText());
        assertEquals(response.getBody().getErrors().get(0).getCode(), new Integer(1003));
        assertEquals(response.getBody().getErrors().get(0).getDescription(), "Text Id {} in the request is not valid");
    }
    
    private TextDomain createTextDomain(UUID textId, String arbitraryText) {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(textId);
        textDomain.setArbitraryText(arbitraryText);
        return textDomain;
    }

    private TextResponse createTextResponse(String textId, String arbitraryText, List<Error> errors) {
        TextResponse textResponse = new TextResponse();
        textResponse.setTextId(textId.toString());
        textResponse.setArbitraryText(arbitraryText);
        if (errors != null) {
            textResponse.setErrors(errors);
        }
        return textResponse;
    }
}
