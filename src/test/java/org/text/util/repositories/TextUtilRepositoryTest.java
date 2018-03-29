package org.text.util.repositories;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.dao.TextUtilDAO;
import org.text.util.domain.TextDomain;
import org.text.util.entity.TextUtilEntity;
import org.text.util.exceptions.TextNotFoundException;

public class TextUtilRepositoryTest {

    @Mock
    private TextUtilDAO textUtilDao;

    @InjectMocks
    private TextUtilRepository textUtilRepository;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTextToDBTest() {
        doNothing().when(textUtilDao).insert(any());
        TextDomain textDomain = createTextDomain();
        textUtilRepository.saveText(textDomain);
        verify(textUtilDao, times(1)).insert(createTextUtilEntity(textDomain));
    }

    @Test
    public void readTextFromDBSuccessfulTest() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        TextUtilEntity textUtilEntity = new TextUtilEntity();
        textUtilEntity.setTextId(textDomain.getTextId());
        textUtilEntity.setArbitraryText("hello");
        doReturn(textUtilEntity).when(textUtilDao).read(textDomain.getTextId());

        TextDomain resultDomain = textUtilRepository.readText(textDomain);
        assertEquals(resultDomain.getTextId(), UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        assertEquals(resultDomain.getArbitraryText(), "hello");
        verify(textUtilDao, times(1)).read(textDomain.getTextId());
    }

    @Test(expectedExceptions = { TextNotFoundException.class })
    public void textIdNotFoundTest() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        doReturn(null).when(textUtilDao).read(textDomain.getTextId());
        textUtilRepository.readText(textDomain);
    }

    @Test
    public void deleteTextFromDBSuccessfulTest() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        TextUtilEntity textUtilEntity = new TextUtilEntity();
        textUtilEntity.setTextId(textDomain.getTextId());
        doNothing().when(textUtilDao).delete(textUtilEntity);

        TextDomain resultDomain = textUtilRepository.deleteText(textDomain);
        assertEquals(resultDomain.getTextId(), UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        verify(textUtilDao, times(1)).delete(textUtilEntity);
    }

    private TextDomain createTextDomain() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        textDomain.setArbitraryText("hello");
        textDomain.setCreateTime(new Date());
        textDomain.setLastUpdatedTime(new Date());
        return textDomain;
    }

    private TextUtilEntity createTextUtilEntity(TextDomain textDomain) {
        TextUtilEntity textUtilEntity = new TextUtilEntity();
        textUtilEntity.setTextId(textDomain.getTextId());
        textUtilEntity.setArbitraryText(textDomain.getArbitraryText());
        textUtilEntity.setCreateTime(textDomain.getCreateTime());
        textUtilEntity.setLastUpdatedTime(textDomain.getLastUpdatedTime());
        return textUtilEntity;
    }

}
