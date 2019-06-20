package com.jj.elasticsearch.entity;

import java.util.Date;

/**
 * @ClassName Blog
 * @Description TODO
 * @Author liujunjie
 * @Date 19-6-10 下午1:39
 * @Version 1.0
 **/
public class Blog {

    private String user;
    private String postDate;
    private String message;

    public String getUser() {
        return user;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
