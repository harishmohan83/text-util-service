package org.text.util.transformers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.binding.v1.TextResponse;
import org.text.util.domain.TextDomain;

public class TextResponseTransformerTest {

    @InjectMocks
    private TextResponseTransformer textResponseTransformer;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void textResponseTransformationSuccessfulTest() {
        TextDomain textDomain = createTextDomain();
        TextResponse textResponse = textResponseTransformer.transform(textDomain);
        assertEquals(textResponse.getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertEquals(textResponse.getArbitraryText(), "hello");
    }

    @Test
    public void textEmptyTextResponseTransformationSuccessfulTest() {
        TextDomain textDomain = createTextDomain();
        textDomain.setArbitraryText("");
        TextResponse textResponse = textResponseTransformer.transform(textDomain);
        assertEquals(textResponse.getTextId(), "091c62c5-ea3e-4aaa-9ada-069daef6a803");
        assertNull(textResponse.getArbitraryText());
    }

    private TextDomain createTextDomain() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        textDomain.setArbitraryText("hello");
        return textDomain;
    }

}
