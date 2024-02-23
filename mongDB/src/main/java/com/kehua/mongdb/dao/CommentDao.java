package com.kehua.mongdb.dao;

import com.kehua.mongdb.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment, String> {

}

