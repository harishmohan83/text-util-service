package org.text.util.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
@RequestScope
@Component
public class TextDomain {
    private UUID textId;
    private String arbitraryText;
    private Date createTime;
    private Date lastUpdatedTime;
}
