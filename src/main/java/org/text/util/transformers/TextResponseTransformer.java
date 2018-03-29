package org.text.util.transformers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.text.util.binding.v1.TextResponse;
import org.text.util.domain.TextDomain;

/**
 * Transformer to transform the create text request in to text domain
 * 
 */
@Component
public class TextResponseTransformer implements ITransformer<TextDomain, TextResponse> {

    /**
     * Populates the textResponse with values from textDomain
     * 
     * @param textDomain
     * @return textResponse
     */
    @Override
    public TextResponse transform(TextDomain textDomain) {
        TextResponse textResponse = new TextResponse();
        textResponse.setTextId(textDomain.getTextId().toString());
        if (StringUtils.isNotBlank(textDomain.getArbitraryText())) {
            textResponse.setArbitraryText(textDomain.getArbitraryText().toString());
        }

        return textResponse;
    }

}
