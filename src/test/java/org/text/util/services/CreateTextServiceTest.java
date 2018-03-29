package org.text.util.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.text.util.domain.TextDomain;
import org.text.util.repositories.TextUtilRepository;

public class CreateTextServiceTest {

    @Mock
    private TextUtilRepository textUtilRepository;

    @InjectMocks
    private CreateTextService createTextService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTextToDBTest() {
        TextDomain textDomain = createTextDomain();
        doReturn(textDomain).when(textUtilRepository).saveText(any());
        createTextService.service(textDomain);
        verify(textUtilRepository, times(1)).saveText(textDomain);
    }

    private TextDomain createTextDomain() {
        TextDomain textDomain = new TextDomain();
        textDomain.setTextId(UUID.fromString("091c62c5-ea3e-4aaa-9ada-069daef6a803"));
        textDomain.setArbitraryText("hello");
        textDomain.setCreateTime(new Date());
        textDomain.setLastUpdatedTime(new Date());
        return textDomain;
    }

}
