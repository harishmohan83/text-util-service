package org.text.util.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.text.util.domain.TextDomain;
import org.text.util.gateway.JsonPlaceHolderResource;

/**
 * Repository class for the Json Place Holder Service call
 * 
 */
@Repository
public class JsonPlaceHolderRepository implements IRepository<TextDomain, TextDomain> {

    @Autowired
    private JsonPlaceHolderResource jsonPlaceHolderResource;
    /**
     * Invokes the Json Place Holder resource to make the http call and fetch
     * the data
     * 
     * @param void
     * @return string
     */
    public String fetchJsonPlaceHolderData() {
        return jsonPlaceHolderResource.getJsonPlaceHolderData();
    }

}
