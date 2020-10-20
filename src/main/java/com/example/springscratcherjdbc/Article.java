package com.example.springscratcherjdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
public class Article {
  @Id
  private long id;
  private String title;
  private String content;
  @MappedCollection(idColumn = "article_id")
  private Set<Comment> comments;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  @PersistenceConstructor
  public Article( String title, String content, Set<Comment> comments, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.title = title;
    this.content = content;
    this.comments = comments;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Article(String title, String content){
    this.title = title;
    this.content = content;
  }

  public Set<Comment> addComment(Comment... comment){
    if(this.comments == null){
      comments = new HashSet<>();
    }
    comments.addAll(new ArrayList(Arrays.asList(comment))); // perhaps cause cannot remove problem below
    return comments;
  }
  public Set<Comment> removeComment(Comment comment){
    if(this.comments == null){
      return null;
    }
    long commentId = comment.getId();
//    boolean f = comments.removeIf(c->c.getId() == commentId);
//      comments.remove(comment); // is Set Immutable?

    ArrayList<Comment> list = new ArrayList<>(comments);
    list.removeIf(c->c.getId()==commentId);
    comments = list.stream().collect(Collectors.toSet());
    return comments;
  }
}
