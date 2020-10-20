package com.example.springscratcherjdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter @ToString
public class Article {
  @Id
  private long id;
  @MappedCollection(idColumn = "article_id", keyColumn = "author_id")
  private AuthorRef author;
  private String title;
  private String content;
  @MappedCollection(idColumn = "article_id", keyColumn = "article_key")
  private List<Comment> comments;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  @PersistenceConstructor
  public Article(AuthorRef author, String title, String content, List<Comment> comments, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.author = author;
    this.title = title;
    this.content = content;
    this.comments = comments;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public Article(Author author, String title, String content){
    this.author = createAuthorRef(author);
    this.title = title;
    this.content = content;
  }

  public List<Comment> addComment(Comment... comment){
    if(this.comments == null){
      comments = new ArrayList();
    }
    comments.addAll(new ArrayList(Arrays.asList(comment)));
    return comments;
  }
  public List<Comment> removeComment(Comment comment){
    if(this.comments == null){
      return null;
    }
    comments.remove(comment);
    return comments;
  }

  public void setAuthor(Author author){
    this.author = createAuthorRef(author);
  }

  private AuthorRef createAuthorRef(Author author){
    AuthorRef authorRef = new AuthorRef();
    authorRef.setAuthorId(author.getId());
    return authorRef;
  }
}
