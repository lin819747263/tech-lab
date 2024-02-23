package com.kehua.mongdb.entity;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "test")
public class Comment {
    @Id
    private String id;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评价人
     */
    private Integer commentUser;

    /**
     * 评论帖子id
     */
    private String commentBlog;

    /**
     * 点赞数
     */
    private Integer commentGood;

    /**
     * 评论时间
     */
    private String createdTime;

    /**
     * 评论时间
     */
    private User user;



    @Data
    public static class User{
        private Long userId;
        private String username;

        private String email;

        private String mobile;
    }

}
