package com.rumofuture.kinner.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class Follow extends BmobObject {

    private User author;
    private User follower;

    public Follow() {

    }

    public Follow(User author, User follower) {
        this.author = author;
        this.follower = follower;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
