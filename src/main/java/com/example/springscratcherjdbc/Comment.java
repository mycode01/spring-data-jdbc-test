package com.example.springscratcherjdbc;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@ToString
public class Comment {
  @Id
  private long id;
  private String name;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  @PersistenceConstructor
  public Comment(String name, String content, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.name = name;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Comment(String name, String content){
    this.name = name;
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Comment comment = (Comment) o;

    if (id != comment.id) {
      return false;
    }
    if (name != null ? !name.equals(comment.name) : comment.name != null) {
      return false;
    }
    return content != null ? content.equals(comment.content) : comment.content == null;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
