package org.text.util.transformers;

import static org.testng.Assert.assertEquals;

import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.domain.TextDomain;
import org.text.util.exceptions.TextUtilValidationException;

public class TextReadRequestTransformerTest {

    @InjectMocks
    private TextReadRequestTransformer textReadRequestTransformer;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readTextRequestTransformationSuccessfulTest() {
        TextDomain textDomain = textReadRequestTransformer.transform("091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertEquals(textDomain.getTextId(), UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
    }

    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Text Id in the request is null or empty")
    public void emptyTextIdReadRequestTransformationTest() {
        textReadRequestTransformer.transform(null);
    }
    
    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Text Id in the request is not valid")
    public void invalidTextIdReadRequestTransformationTest() {
        textReadRequestTransformer.transform("091c62c5-ea3&");
    }

}
