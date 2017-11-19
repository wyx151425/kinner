package com.rumofuture.kinner.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class Review extends BmobObject {

    private Album album;
    private User reviewer;
    private String content;

    public Review() {

    }

    public Review(Album album, User reviewer) {
        this.album = album;
        this.reviewer = reviewer;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
