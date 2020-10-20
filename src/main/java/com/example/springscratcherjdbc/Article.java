package com.example.springscratcherjdbc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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
//  @Column("author_id")
  @MappedCollection(idColumn = "article_id", keyColumn = "author_id")
  private AuthorRef author;
  private String title;
  private String content;
  @MappedCollection(idColumn = "article_id")
  private Set<Comment> comments;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  @PersistenceConstructor
  public Article(AuthorRef author, String title, String content, Set<Comment> comments, LocalDateTime createdAt,
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

  public void setAuthor(Author author){
    this.author = createAuthorRef(author);
  }

  private AuthorRef createAuthorRef(Author author){
    AuthorRef authorRef = new AuthorRef();
    authorRef.setAuthorId(author.getId());
    return authorRef;
  }
}
