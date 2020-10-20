package com.example.springscratcherjdbc;

import org.springframework.data.relational.core.mapping.Table;

@Table("article_author")
public class AuthorRef {
  private long authorId;
  public void setAuthorId(long authorId){
    this.authorId = authorId;
  }
  public long getAuthorId(){
    return authorId;
  }
}
