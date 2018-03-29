package org.text.util.repositories;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.gateway.JsonPlaceHolderResource;

public class JsonPlaceholderRepositoryTest {

    @Mock
    private JsonPlaceHolderResource jsonPlaceHolderResource;

    @InjectMocks
    private JsonPlaceHolderRepository jsonPlaceHolderRepository;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void jsonPlaceHolderRepositoryTest() {
        doReturn("json").when(jsonPlaceHolderResource).getJsonPlaceHolderData();
        jsonPlaceHolderRepository.fetchJsonPlaceHolderData();
        verify(jsonPlaceHolderResource, times(1)).getJsonPlaceHolderData();
    }

}
