package com.example.springscratcherjdbc;

import org.springframework.data.repository.CrudRepository;

public interface CommentRespository extends CrudRepository<Comment, Long> {

}
