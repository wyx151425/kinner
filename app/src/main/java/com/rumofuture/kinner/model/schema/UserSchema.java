package com.rumofuture.kinner.model.schema;

/**
 * Created by WangZhenqi on 2017/2/7.
 */

public class UserSchema {

    public static final class Table {
        public static final String NAME = "_User";

        public static final class Cols {
            public static final String OBJECT_ID = "objectId";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
            public static final String MOBILE_PHONE_NUMBER_VERIFIED = "mobilePhoneNumberVerified";
            public static final String EMAIL = "email";
            public static final String EMAIL_VERIFIED = "emailVerified";
            public static final String AUTH_DATA = "authData";
            public static final String CREATE_TIME = "createAt";
            public static final String UPDATE_TIME = "updateAt";

            public static final String NAME = "name";
            public static final String MOTTO = "motto";
            public static final String PROFILE = "profile";
            public static final String PROFESSION = "profession";
            public static final String LOCATION = "location";

            public static final String GENDER = "gender";
            public static final String BIRTHDAY = "birthday";

            public static final String AGE = "age";
            public static final String FOLLOW_TOTAL = "followTotal";
            public static final String FOLLOWER_TOTAL = "followerTotal";
            public static final String FAVORITE_TOTAL = "favoriteTotal";
            public static final String ALBUM_TOTAL = "albumTotal";

            public static final String AVATAR = "avatar";
            public static final String PORTRAIT = "portrait";

            public static final String AUTHORIZE = "authorize";
        }
    }
}
