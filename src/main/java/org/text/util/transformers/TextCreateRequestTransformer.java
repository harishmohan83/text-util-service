package org.text.util.transformers;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.text.util.binding.v1.TextCreateRequest;
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
public class TextCreateRequestTransformer implements ITransformer<TextCreateRequest, TextDomain> {

    private final String regex = "^[a-zA-Z0-9_@]*$";

    /**
     * Populates the textDomain with values from textCreateRequest
     * 
     * @param textCreateRequest
     * @return textDomain
     */
    @Override
    public TextDomain transform(TextCreateRequest textCreateRequest) {
        validateTextRequest(textCreateRequest);
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.randomUUID());
        textDomain.setArbitraryText(textCreateRequest.getArbitraryText());
        textDomain.setCreateTime(new Date());
        return textDomain;
    }

    private void validateTextRequest(TextCreateRequest textRequest) {
        if (textRequest == null || StringUtils.isBlank(textRequest.getArbitraryText())) {
            log.debug("Input text is null or empty");
            throw new TextUtilValidationException(ErrorEnum.TEXT_NULL_OR_EMPTY, "Input text is null or empty");
        }
        if (!textRequest.getArbitraryText().matches(regex)) {
            log.debug("Unacceptable characters in input text {}", textRequest.getArbitraryText());
            throw new TextUtilValidationException(ErrorEnum.TEXT_NOT_VALID, "Unacceptable characters in input text",
                    textRequest.getArbitraryText(), null);
        }
        return;
    }

}
