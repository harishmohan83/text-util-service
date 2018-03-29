package org.text.util.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.text.util.repositories.JsonPlaceHolderRepository;

/**
 * Service to create the arbitrary text
 * 
 */
@Service
public class JsonPlaceHolderService implements IService<String, String> {

    @Autowired
    private JsonPlaceHolderRepository jsonPlaceHolderRepository;

    /**
     * Invokes the repository layer to fetch the json place holder data
     * 
     * @param string
     * @return string
     */
    @Override
    public String service(String str) {
        return jsonPlaceHolderRepository.fetchJsonPlaceHolderData();
    }

}
