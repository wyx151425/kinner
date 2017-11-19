package com.rumofuture.kinner.model.schema;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public class ReviewSchema {

    public static final class Table {
        public static final String NAME = "Review";

        public static final class Cols {
            public static final String OBJECT_ID = "objectId";
            public static final String CREATE_TIME = "createAt";
            public static final String UPDATE_TIME = "updateAt";

            public static final String ALBUM = "album";
            public static final String REVIEWER = "reviewer";
            public static final String CONTENT = "content";
        }
    }
}
