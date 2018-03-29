package org.text.util.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.repositories.JsonPlaceHolderRepository;

public class JsonPlaceholderServiceTest {

    @Mock
    private JsonPlaceHolderRepository jsonPlaceHolderRepository;

    @InjectMocks
    private JsonPlaceHolderService jsonPlaceHolderService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void jsonPlaceHolderServiceTest() {
        doReturn("json").when(jsonPlaceHolderRepository).fetchJsonPlaceHolderData();
        jsonPlaceHolderService.service("");
        verify(jsonPlaceHolderRepository, times(1)).fetchJsonPlaceHolderData();
    }

}
