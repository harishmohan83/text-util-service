package org.text.util.transformers;

import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.binding.v1.TextCreateRequest;
import org.text.util.domain.TextDomain;
import org.text.util.exceptions.TextUtilValidationException;

public class TextCreateRequestTransformerTest {

    @Mock
    private TextCreateRequest textCreateRequest;

    @InjectMocks
    private TextCreateRequestTransformer textCreateRequestTransformer;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTextRequestTransformationSuccessfulTest() {
        doReturn("hello@_").when(textCreateRequest).getArbitraryText();
        TextDomain textDomain = textCreateRequestTransformer.transform(textCreateRequest);
        assertEquals(textDomain.getArbitraryText(), "hello@_");
    }

    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Input text is null or empty")
    public void emptyTextCreateRequestTransformationTest() {
        doReturn(null).when(textCreateRequest).getArbitraryText();
        textCreateRequestTransformer.transform(textCreateRequest);
    }
    
    @Test(expectedExceptions = {
            TextUtilValidationException.class }, expectedExceptionsMessageRegExp = "Unacceptable characters in input text")
    public void InValidTextCreateRequestTransformationTest() {
        doReturn("dhjfewf@_&&&").when(textCreateRequest).getArbitraryText();
        textCreateRequestTransformer.transform(textCreateRequest);
    }

}
