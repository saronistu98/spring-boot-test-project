package com.saron.spring.test.user.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue
    private long id;
    @Version
    private long version;
    @Column(nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now();
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @PreUpdate
    @PrePersist
    public void setUpdateDateAtPersist() {
        updateDate = LocalDateTime.now();
    }

}
