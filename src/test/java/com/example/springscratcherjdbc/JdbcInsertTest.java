package com.example.springscratcherjdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcInsertTest {

  @Autowired
  ArticleRepository articleRepository;
  @Autowired
  AuthorRepository authorRepository;
  @Autowired
  CommentRespository commentRespository;

  @Test
  public void stage01_insert(){
    Article article = new Article("새로운 테스트1", "테스트 일번 ");
    Comment comment1 = new Comment("코멘트1", "테스트1에 대한 코멘트");
    Comment comment2 = new Comment("코멘트2", "테스트1에 대한 코멘트2");
    article.addComment(comment1, comment2);
    long article_id = articleRepository.save(article).getId();
    Article articleCopy = articleRepository.findById(article_id).get();
    assertThat(article.getTitle(), is(articleCopy.getTitle()));
    assertThat(article.getContent(), is(articleCopy.getContent()));
    assertThat(article.getComments().size(), is(2));
    assertThat(articleCopy.getComments(), hasItem(comment1));
    System.out.println(articleCopy.getComments());

    Article article2 = new Article("새로운 테스트2", "테스트 이번 ");
    Comment comment3 = new Comment("코멘트1", "테스트2에 대한 코멘트");
    Comment comment4 = new Comment("코멘트2", "테스트2에 대한 코멘트2");
    Comment comment5 = new Comment("코멘트3", "테스트2에 대한 코멘트3");
    article2.addComment(comment3, comment4, comment5);
    articleRepository.save(article2);


    article.removeComment(comment1);
    article_id = articleRepository.save(article).getId();
    articleCopy = articleRepository.findById(article_id).get();
    assertThat(articleCopy.getComments().size(), is(1));
    System.out.println(articleCopy.getComments());
  }

}
