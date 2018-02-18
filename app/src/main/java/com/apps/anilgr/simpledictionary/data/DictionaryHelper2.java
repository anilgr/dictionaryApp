package com.apps.anilgr.simpledictionary.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by anilgr on 5/2/18.
 */

public class DictionaryHelper2 extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "Dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public DictionaryHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
