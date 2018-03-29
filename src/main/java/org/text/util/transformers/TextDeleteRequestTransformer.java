package org.text.util.transformers;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.text.util.binding.v1.TextDeleteRequest;
import org.text.util.domain.TextDomain;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.TextUtilValidationException;

/**
 * Transformer to transform the create text request in to text domain
 * 
 */
@Component
public class TextDeleteRequestTransformer implements ITransformer<TextDeleteRequest, TextDomain> {

    private final String regex = "^[a-zA-Z0-9-]*$";

    /**
     * Populates the textDomain with values from textCreateRequest
     * 
     * @param textDeleteRequest
     * @return textDomain
     */
    @Override
    public TextDomain transform(TextDeleteRequest textDeleteRequest) {
        validateTextDeleteRequest(textDeleteRequest);
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString(textDeleteRequest.getTextId()));
        textDomain.setCreateTime(new Date());
        return textDomain;
    }

    private void validateTextDeleteRequest(TextDeleteRequest textDeleteRequest) {
        if (textDeleteRequest == null || StringUtils.isBlank(textDeleteRequest.getTextId())) {
            throw new TextUtilValidationException(ErrorEnum.TEXT_ID_NULL_OR_EMPTY,
                    "Text Id in the request is null or empty");
        }
        if (!textDeleteRequest.getTextId().matches(regex)) {
            throw new TextUtilValidationException(ErrorEnum.TEXT_ID_NOT_VALID, "Text Id in the request is not valid",
                    null, textDeleteRequest.getTextId());
        }
        return;
    }

}
