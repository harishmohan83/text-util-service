package org.text.util.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.text.util.dao.TextUtilDAO;
import org.text.util.domain.TextDomain;
import org.text.util.entity.TextUtilEntity;
import org.text.util.enums.ErrorEnum;
import org.text.util.exceptions.TextNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Repository class for the Text Util table
 * 
 */
@Repository
@Slf4j
public class TextUtilRepository implements IRepository<TextDomain, TextDomain> {

    @Autowired
    private TextUtilDAO textutilDao;

    /**
     * Invokes the TextUtil DAO to save the text
     * 
     * @param textDomain
     * @return textDomain
     */
    public TextDomain saveText(TextDomain textDomain) {
        textutilDao.insert(toEntity(textDomain));
        return textDomain;
    }

    /**
     * Invokes the TextUtil DAO to read the text from text_util table by
     * partition key
     * 
     * @param textDomain
     * @return textDomain
     */
    public TextDomain readText(TextDomain textDomain) {
        TextUtilEntity textUtilEntity = textutilDao.read(textDomain.getTextId());
        if (textUtilEntity == null) {
            log.debug("The text id {} was not found", textDomain.getTextId());
            throw new TextNotFoundException(ErrorEnum.TEXT_ID_NOT_FOUND, "The text Id was not found",
                    textDomain.getTextId().toString());
        }
        textDomain = textUtilEntity.toDomain();
        return textDomain;
    }

    /**
     * Deletes the text from text_util table
     * 
     * @param textDomain
     * @return void
     */
    public TextDomain deleteText(TextDomain textDomain) {
        textutilDao.delete(toEntity(textDomain));
        return textDomain;
    }

    private TextUtilEntity toEntity(TextDomain textDomain) {
        return new TextUtilEntity(textDomain.getTextId(), textDomain.getArbitraryText(), textDomain.getCreateTime(),
                textDomain.getLastUpdatedTime());
    }
}
