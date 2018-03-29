package org.text.util.entity;

import java.util.Date;
import java.util.UUID;

import org.springframework.web.context.annotation.RequestScope;
import org.text.util.domain.TextDomain;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.PartitionKey;
import info.archinnov.achilles.annotations.Table;
import lombok.EqualsAndHashCode;

@Table(keyspace = "utils", table = "text_util")
@EqualsAndHashCode
@RequestScope
public class TextUtilEntity {
    
    @PartitionKey
    @Column("text_id")
    private UUID textId;
    
    @Column("arbitrary_text")
    private String arbitraryText;

    @Column("create_time")
    private Date createTime;

    @Column("last_upd_time")
    private Date lastUpdatedTime;

    public TextUtilEntity() {

    }

    public TextUtilEntity(UUID textId, String arbitraryText, Date createTime, Date lastUpdatedTime) {
        this.textId = textId;
        this.arbitraryText = arbitraryText;
        this.createTime = createTime;
        this.lastUpdatedTime = lastUpdatedTime;
    }
    
    public void setTextId(UUID textId) {
        this.textId = textId;
    }

    public UUID getTextId() {
        return this.textId;
    }

    public void setArbitraryText(String arbitraryText) {
        this.arbitraryText = arbitraryText;
    }

    public String getArbitraryText() {
        return this.arbitraryText;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public TextDomain toDomain() {
        TextDomain domain = new TextDomain();
        domain.setTextId(this.getTextId());
        domain.setArbitraryText(this.getArbitraryText());
        domain.setCreateTime(this.getCreateTime());
        domain.setLastUpdatedTime(this.getLastUpdatedTime());
        return domain;
    }
}
