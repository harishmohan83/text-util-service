package org.text.util.transformers;

import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.assertEquals;

import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.binding.v1.TextDeleteRequest;
import org.text.util.domain.TextDomain;
import org.text.util.exceptions.TextUtilValidationException;

public class TextDeleteRequestTransformerTest {

    @Mock
    private TextDeleteRequest textDeleteRequest;
    
    @InjectMocks
    private TextDeleteRequestTransformer textDeleteRequestTransformer;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteTextRequestTransformationSuccessfulTest() {
        doReturn("091c62c5-ea3e-4aaa-9ada-069daef6a803").when(textDeleteRequest).getTextId();
        TextDomain textDomain = textDeleteRequestTransformer.transform(textDeleteRequest);
        assertEquals(textDomain.getTextId(), UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
    }

    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Text Id in the request is null or empty")
    public void emptyTextIdReadRequestTransformationTest() {
        doReturn("").when(textDeleteRequest).getTextId();
        textDeleteRequestTransformer.transform(textDeleteRequest);
    }
    
    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Text Id in the request is not valid")
    public void invalidTextIdReadRequestTransformationTest() {
        doReturn("091c62c5-ea3e-4aaa&").when(textDeleteRequest).getTextId();
        textDeleteRequestTransformer.transform(textDeleteRequest);
    }

}
