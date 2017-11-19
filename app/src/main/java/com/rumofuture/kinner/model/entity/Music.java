package com.rumofuture.kinner.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public class Music extends BmobObject {

    private Album album;  // 所属专辑
    private String name;  // 名称
    private BmobFile music;  // 音乐

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobFile getMusic() {
        return music;
    }

    public void setMusic(BmobFile music) {
        this.music = music;
    }
}
