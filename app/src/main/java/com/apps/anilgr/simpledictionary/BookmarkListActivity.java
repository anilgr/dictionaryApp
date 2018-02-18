package com.apps.anilgr.simpledictionary;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.apps.anilgr.simpledictionary.data.DictionaryContract;
import com.apps.anilgr.simpledictionary.data.DictionaryHelper2;
import com.apps.anilgr.simpledictionary.data.Word;

import java.util.ArrayList;


/**
 * Created by anilgr on 12/2/18.
 */

public class BookmarkListActivity extends AppCompatActivity {
    private static final String TAG = "Bookmarklisttag:";
    private ListView mList;
    private  ArrayList<Word> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bookmark);
        mList = findViewById(R.id.listview);

        mList.setAdapter(new BookmarkAdapter(this,
                list = Utility.getBookmarks(getApplication())));

    }

}
