package com.apps.anilgr.simpledictionary.data;

import android.provider.BaseColumns;

/**
 * Created by anilgr on 5/2/18.
 */

public class DictionaryContract {

    public static class Words implements BaseColumns {

        public static final String TABLE_NAME = "words";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_DEFINITION = "defn";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ISBOOKMARKED = "isbookmarked";


    }
}
