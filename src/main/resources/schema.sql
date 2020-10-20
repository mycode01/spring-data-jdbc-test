drop table if exists comment;
drop table if exists article_author;
drop table if exists article;
drop table if exists author;

create table author(
	id int(11) not null auto_increment,
    name varchar(255) not null,
    author_rank varchar(10) not null,
    created_at datetime null,
    updated_at datetime null,
    PRIMARY KEY (`id`)
);

create table article (
    id int(11) not null AUTO_INCREMENT,
    title varchar(255) not null,
    content varchar(255) null,
    created_at datetime null,
    updated_at datetime null,
    PRIMARY KEY (`id`)
);

CREATE TABLE article_author (
  article_id int(11) NOT NULL,
  author_id int(11) NOT NULL,
  KEY `idx_article_id` (`article_id`),
  KEY `idx_author_id` (`author_id`)
) ;

CREATE TABLE comment (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  content varchar(255) DEFAULT NULL,
  article_id int(11) NOT NULL,
  created_at datetime null,
  updated_at datetime null,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
);
insert into author(name, author_rank) value ('김호구', 'SPECIAL');