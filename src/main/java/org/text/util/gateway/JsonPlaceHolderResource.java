package org.text.util.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.text.util.exceptions.JsonPlaceHolderException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonPlaceHolderResource {

    @Autowired
    private RestTemplate jsonRestTemplate;

    @Value("${gateway.jsonplaceholder.url}")
    private String jsonPlaceholderUrl;

    /**
     * Makes an http call to json place holder api
     *
     * @param void
     * @return string
     */
    public String getJsonPlaceHolderData() {
        log.debug("Entering json place holder resource to make a rest call to Json Place Holder end point");
        ResponseEntity<String> response = null;
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Accept", MediaType.TEXT_PLAIN_VALUE);
            HttpEntity<String> request = new HttpEntity<>(headers);

            response = jsonRestTemplate.exchange(jsonPlaceholderUrl, HttpMethod.GET, request, String.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                log.debug("Completed the rest call to Json Place Holder end point");
                return response.getBody();
            }

        } catch (Exception r) {
            log.error("Http call to Json placeholder failed", r);
            throw new JsonPlaceHolderException(r.getMessage());
        }
        log.error("Json placeholder service returned the {} status code", response.getStatusCode().value());
        throw new JsonPlaceHolderException(
                "Json placeholder service returned " + response.getStatusCode().value() + " status code");
    }

}
