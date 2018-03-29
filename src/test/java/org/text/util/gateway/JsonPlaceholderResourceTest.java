package org.text.util.gateway;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.exceptions.JsonPlaceHolderException;

public class JsonPlaceholderResourceTest {

    @Mock
    private ResponseEntity<String> response;

    @Mock
    private RestTemplate jsonRestTemplate;

    @InjectMocks
    private JsonPlaceHolderResource jsonPlaceHolderResource;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void jsonPlaceHolderSuccessfulCallTest() {
        doReturn(HttpStatus.OK).when(response).getStatusCode();
        doReturn("json").when(response).getBody();
        doReturn(response).when(jsonRestTemplate).exchange(any(String.class), any(HttpMethod.class),
                any(HttpEntity.class), same(String.class));
        assertEquals("json", jsonPlaceHolderResource.getJsonPlaceHolderData());
        verify(jsonRestTemplate, times(1)).exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class),
                same(String.class));
    }

    @Test(expectedExceptions = {
            JsonPlaceHolderException.class }, expectedExceptionsMessageRegExp = "Json placeholder service returned 500 status code")
    public void jsonPlaceHolder500ErrorTest() {
        doReturn(HttpStatus.INTERNAL_SERVER_ERROR).when(response).getStatusCode();
        doReturn(null).when(response).getBody();
        doReturn(response).when(jsonRestTemplate).exchange(any(String.class), any(HttpMethod.class),
                any(HttpEntity.class), same(String.class));
        jsonPlaceHolderResource.getJsonPlaceHolderData();
        verify(jsonRestTemplate, times(1)).getForEntity(any(String.class), anyObject());
    }

    @Test(expectedExceptions = { JsonPlaceHolderException.class }, expectedExceptionsMessageRegExp = "connection error")
    public void jsonPlaceHolderConnectionErrorTest() {
        doThrow(new ResourceAccessException("connection error")).when(jsonRestTemplate).exchange(any(String.class),
                any(HttpMethod.class), any(HttpEntity.class), same(String.class));
        jsonPlaceHolderResource.getJsonPlaceHolderData();
        verify(jsonRestTemplate, times(1)).getForEntity(any(String.class), anyObject());
    }

}
