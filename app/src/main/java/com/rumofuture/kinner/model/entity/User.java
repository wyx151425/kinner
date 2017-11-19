package com.rumofuture.kinner.model.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2016/12/24.
 */

public class User extends BmobUser {

    private String name;  // 姓名
    private String motto;  // 座右铭
    private String profile;  // 简介
    private String profession;
    private String location;  // 所在地

    private String gender;  // 性别
    private String birthday;  // 生日

    private Integer age;  // 年龄
    private Integer followTotal;  // 关注作家数
    private Integer followerTotal;  // 粉丝数
    private Integer favoriteTotal; // 收藏漫画册数
    private Integer albumTotal;  // 漫画册数量

    private BmobFile avatar;  // 头像
    private BmobFile portrait;  // 个人肖像

    private Boolean authorize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getFollowTotal() {
        return followTotal;
    }

    public void setFollowTotal(Integer followTotal) {
        this.followTotal = followTotal;
    }

    public Integer getFollowerTotal() {
        return followerTotal;
    }

    public void setFollowerTotal(Integer followerTotal) {
        this.followerTotal = followerTotal;
    }

    public Integer getFavoriteTotal() {
        return favoriteTotal;
    }

    public void setFavoriteTotal(Integer favoriteTotal) {
        this.favoriteTotal = favoriteTotal;
    }

    public Integer getAlbumTotal() {
        return albumTotal;
    }

    public void setAlbumTotal(Integer albumTotal) {
        this.albumTotal = albumTotal;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobFile getPortrait() {
        return portrait;
    }

    public void setPortrait(BmobFile portrait) {
        this.portrait = portrait;
    }

    public Boolean getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Boolean authorize) {
        this.authorize = authorize;
    }
}
