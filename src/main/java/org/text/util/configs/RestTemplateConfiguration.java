package org.text.util.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Maintains the bean configuration for all {@link RestTemplate} beans used by
 * the application
 */
@Configuration
public class RestTemplateConfiguration {

    @Autowired
    private ClientHttpRequestFactory httpRequestFactory;

    @Bean(name = "jsonRestTemplate")
    public RestTemplate jsonRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }

}