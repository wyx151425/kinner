package com.rumofuture.kinner.model.schema;

/**
 * Created by WangZhenqi on 2017/2/7.
 */

public class AlbumSchema {

    public static final class Table {
        public static final String NAME = "Album";

        public static final class Cols {
            public static final String OBJECT_ID = "objectId";
            public static final String CREATE_TIME = "createAt";
            public static final String UPDATE_TIME = "updateAt";

            public static final String AUTHOR = "author";

            public static final String NAME = "name";
            public static final String STYLE = "style";
            public static final String INTRODUCTION = "introduction";

            public static final String MUSIC_TOTAL = "musicTotal";
            public static final String FAVOR_TOTAL = "favorTotal";

            public static final String COVER = "cover";

            public static final String APPROVE = "approve";
            public static final String SHOW = "show";
        }
    }
}
