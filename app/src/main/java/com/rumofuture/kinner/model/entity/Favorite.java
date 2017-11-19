package com.rumofuture.kinner.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class Favorite extends BmobObject {

    private Album album;
    private User favor;

    public Favorite() {

    }

    public Favorite(Album album, User favor) {
        this.album = album;
        this.favor = favor;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public User getFavor() {
        return favor;
    }

    public void setFavor(User favor) {
        this.favor = favor;
    }
}
