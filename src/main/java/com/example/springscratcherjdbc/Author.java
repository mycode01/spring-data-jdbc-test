package com.example.springscratcherjdbc;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Author {
  @Id
  private long id;
  private String name;
  private AuthorRank authorRank;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  @PersistenceConstructor
  public Author(long id, String name, AuthorRank authorRank, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.authorRank = authorRank;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Author(String name){
    this.name = name;
    this.authorRank = AuthorRank.NORMAL;
  }
}
