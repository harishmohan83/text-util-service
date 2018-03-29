package org.text.util.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.text.util.entity.TextUtilEntity;

import info.archinnov.achilles.generated.ManagerFactory;

/**
 * DAO for Text Util table
 * 
 */
@Component
public class TextUtilDAO {

    @Autowired
    private ManagerFactory managerFactory;

    /**
     * Saves the text in to text_util table
     * 
     * @param entity
     * @return void
     */
    public void insert(TextUtilEntity entity) {
        managerFactory.forTextUtilEntity().crud().insert(entity).execute();
    }

    /**
     * Reads the text from text_util table
     * 
     * @param id
     * @return textUtilEntity
     */
    public TextUtilEntity read(UUID id) {
        return managerFactory.forTextUtilEntity().crud().findById(id).get();
    }
    
    /**
     * Deletes the text from text_util table
     * 
     * @param entity
     * @return void
     */
    public void delete(TextUtilEntity entity) {
        managerFactory.forTextUtilEntity().crud().delete(entity).execute();
    }
}
