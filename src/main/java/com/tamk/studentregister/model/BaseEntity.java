package com.tamk.studentregister.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected long id;

  @Column(updatable = false)
  @CreatedDate
  protected LocalDateTime createdAt;

  @LastModifiedDate
  protected LocalDateTime updatedAt;
}
