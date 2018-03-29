package org.text.util.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.text.util.domain.TextDomain;
import org.text.util.repositories.TextUtilRepository;

/**
 * Service to create the arbitrary text
 * 
 */
@Service
public class ReadTextService implements IService<TextDomain, TextDomain> {
    
    @Autowired
    private TextUtilRepository textUtilRepository;

    /**
     * Invokes the repository layer to read the text from database
     * 
     * @param textDomain
     * @return textDomain
     */
    @Override
    public TextDomain service(TextDomain textDomain) {
        return textUtilRepository.readText(textDomain);
    }

}
