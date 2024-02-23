package com.kehua.mongdb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.kehua.mongdb.DataProvider;
import com.kehua.mongdb.dao.CommentDao;
import com.kehua.mongdb.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {


    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CommentDao commentDao;

    public void save(){

        Comment comment = new Comment();

        comment.setId("e00000001");
        comment.setCommentBlog("dsafsdfsd");
        comment.setCommentGood(88);
        comment.setCommentUser(66);
        comment.setCreatedTime("2024-02-23 12:45:23");
        comment.setCommentContent("sdfsfewrwrfwerwerewrwefsdfwesfsfwfwfrwfger");


        Comment.User user = new Comment.User();
        user.setUserId(10L);
        user.setUsername("lin819747263");
        user.setMobile("15898563256");
        user.setEmail("8197487263@qq.com");

        comment.setUser(user);

        commentDao.save(comment);
    }



    public void saveJson(){
        List<JSONObject> jsonObjects = DataProvider.gen(10);
        jsonObjects.forEach(x -> {
            mongoTemplate.save(x, "comment");
        });
    }


    public void update(){

        Comment comment = new Comment();

        comment.setId("e00000001");
        comment.setCommentBlog("哈哈哈666999");
        comment.setCommentGood(88);
        comment.setCommentUser(66);
        comment.setCreatedTime("2024-02-23 12:45:23");

        commentDao.save(comment);
    }


    public void query(){
        List<Comment> comments =  commentDao.findAll();
        comments.forEach(System.out::println);
    }

}
