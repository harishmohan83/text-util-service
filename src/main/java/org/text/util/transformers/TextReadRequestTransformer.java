package org.text.util.transformers;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.text.util.domain.TextDomain;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.TextUtilValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * Transformer to transform the create text request in to text domain
 * 
 */
@Component
@Slf4j
public class TextReadRequestTransformer implements ITransformer<String, TextDomain> {

    private final String regex = "^[a-zA-Z0-9-]*$";

    /**
     * Populates the textDomain with values from textCreateRequest
     * 
     * @param textCreateRequest
     * @return textDomain
     */
    @Override
    public TextDomain transform(String textId) {
        validateTextId(textId);
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString(textId));
        return textDomain;
    }

    private void validateTextId(String textId) {
        if (StringUtils.isBlank(textId)) {
            log.debug("Text Id in the request is null or empty");
            throw new TextUtilValidationException(ErrorEnum.TEXT_ID_NULL_OR_EMPTY,
                    "Text Id in the request is null or empty", null, "Text Id in the request is null or empty");
        }
        if (!textId.matches(regex)) {
            log.debug("Text Id {} in the request is not valid", textId);
            throw new TextUtilValidationException(ErrorEnum.TEXT_ID_NOT_VALID, "Text Id in the request is not valid",
                    null, textId);
        }
    }

}
