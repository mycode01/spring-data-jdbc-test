package com.example.springscratcherjdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  static Article article1;
  static Comment comment1;
  static Comment comment2;

  static Article article2;

  /**
   * Author는 의미상 article의 aggreate안에서 참조가 될 뿐이므로 여기서는 ref타입으로 참조 되도록 함.
   * Author가 aggregate안에 포함되지 않기때문에 정신줄 놓고 new Author(..) 를 그대로 사용하면 레퍼런스 테이블에 id값으로 0이 들어감.
   * 이게 자연스러운 부분이라 프로그래머가 만든 버그에도 눈치채지 못할수도 있겠다.
   * repository를 사용하려면 @BeforeAll로는 불가능하기 때문에 Each사용
   */
  @BeforeEach
  public void init(){
    article1 = new Article(authorRepository.save(new Author("김등신")), "새로운 테스트1", "테스트 일번 ");
    comment1 = new Comment("코멘트1", "테스트1에 대한 코멘트");
    comment2 = new Comment("코멘트2", "테스트1에 대한 코멘트2");
    article1.addComment(comment1, comment2);

    Author author2 = new Author("김번개");
    author2.changeAuthorRank(AuthorRank.PRIME);
    article2 = new Article(authorRepository.save(author2), "새로운 테스트2", "테스트 이번 ");
  }


  @Test
  public void stage01_insert(){
    long article_id = articleRepository.save(article1).getId();
    Article articleCopy = articleRepository.findById(article_id).get();

    assertThat(article1.getTitle(), is(articleCopy.getTitle()));
    assertThat(article1.getContent(), is(articleCopy.getContent()));
    assertThat(article1.getComments().size(), is(2));
    assertThat(articleCopy.getComments(), hasItem(comment1));

    Comment comment3 = new Comment("코멘트1", "테스트2에 대한 코멘트");
    Comment comment4 = new Comment("코멘트2", "테스트2에 대한 코멘트2");
    Comment comment5 = new Comment("코멘트3", "테스트2에 대한 코멘트3");
    article2.addComment(comment3, comment4, comment5);
    articleRepository.save(article2);

    Author specialAuthor = authorRepository.findById(1l).get(); // already exists on table. look schema.sql
    Article article3 = new Article(specialAuthor, "특별한 작가의 특별한 저작물", "매우 특별한 내용");
    article_id = articleRepository.save(article3).getId();
    articleCopy = articleRepository.findById(article_id).get();
    assertThat(article3.getAuthor().getAuthorId(), is(articleCopy.getAuthor().getAuthorId())); // ref 타입이기 때문에 equals가 아닌 아이디값으로 비교


    article1.removeComment(comment1);
    article1.getComments().get(0).setContent("의미없는 내용 ");
    article_id = articleRepository.save(article1).getId();
    articleCopy = articleRepository.findById(article_id).get();
    assertThat(articleCopy.getComments().size(), is(1));
    System.out.println(articleCopy.getComments());
  }

}
