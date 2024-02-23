package com.kehua.mongdb.controller;


import com.alibaba.fastjson.JSON;
import com.kehua.mongdb.DataProvider;
import com.kehua.mongdb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;


    @GetMapping("save")
    public void save(){
        commentService.save();
    }

    @GetMapping("json")
    public void json(){

        commentService.saveJson();
    }


    @GetMapping("update")
    public void update(){
        commentService.update();
    }

    @GetMapping("query")
    public void query(){
        commentService.query();
    }

    @GetMapping("query1")
    public void query1(){
        commentService.query1();
    }
}
