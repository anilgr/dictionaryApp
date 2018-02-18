package com.apps.anilgr.simpledictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apps.anilgr.simpledictionary.data.DictionaryContract;
import com.apps.anilgr.simpledictionary.data.DictionaryHelper2;
import com.apps.anilgr.simpledictionary.data.Word;

import java.util.ArrayList;

/**
 * Created by anilgr on 5/2/18.
 */

public class Utility {

    private static final String TAG = "Bookmarklisttag:";
    public static ArrayList<Word> getBookmarks(Context context){
        ArrayList<Word> list = new ArrayList<>();
        try {
            DictionaryHelper2 dictHelper = new DictionaryHelper2(context);
            SQLiteDatabase db = dictHelper.getReadableDatabase();
            String rawStatement = "SELECT " + DictionaryContract.Words.COLUMN_WORD + "," +DictionaryContract.Words.COLUMN_TYPE+ ","  + DictionaryContract.Words.COLUMN_DEFINITION +","  + DictionaryContract.Words.COLUMN_ISBOOKMARKED +
                    " FROM " + DictionaryContract.Words.TABLE_NAME + " WHERE isbookmarked ==" +
                    " ? GROUP BY word;";
            Cursor cursor = db.rawQuery(rawStatement, new String[]{"1"});
            Log.d(TAG, "queryDatabase: "+cursor.getCount());
            dictHelper.close();

            list = Utility.getWordsFromCursor(cursor);

        }
        catch (SQLException e)
        {
            Log.d(TAG,"Error with database operation");
        }

        return list;
    }
  public static ArrayList<Word> getWordsFromCursor(Cursor cursor)
  {

      ArrayList<Word> words = new ArrayList<>();
      try {
      if (cursor == null)
          return  null;

      cursor.moveToFirst();
      do {
          String name, type, definition;
          name = cursor.getString(cursor.getColumnIndex(DictionaryContract.Words.COLUMN_WORD));
          definition = cursor.getString(cursor.getColumnIndex(DictionaryContract.Words.COLUMN_DEFINITION));
          type = cursor.getString(cursor.getColumnIndex(DictionaryContract.Words.COLUMN_TYPE));
          boolean isBookkmarked = cursor.getInt(cursor.getColumnIndex(DictionaryContract.Words.COLUMN_ISBOOKMARKED))>0;
          Word word  = new Word(name,type, definition,isBookkmarked );
          words.add(word);
      }while (cursor.moveToNext());
  }catch (Exception e)
  {
      //do something
  }

     return  words;
  }
}
