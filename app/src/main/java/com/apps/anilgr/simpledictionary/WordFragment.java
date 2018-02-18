package com.apps.anilgr.simpledictionary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.apps.anilgr.simpledictionary.data.DictionaryContract;
import com.apps.anilgr.simpledictionary.data.DictionaryHelper2;
import com.apps.anilgr.simpledictionary.data.Word;

import java.util.ArrayList;

/**
 * Created by anilgr on 12/2/18.
 */

public class WordFragment extends Fragment {
    public static String TAG  = "DANILGR";
    private TextView mWordTextview;
    TextView mDefinitionTextview;
    private ToggleButton mBookmarkTogglebutton;
    private String queryText;
    private String wordText = "",wordDef = "";
    private boolean wordBookmark;

    public  interface OnQueryByFragment{
        public void updateWords(Word word);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word,container,false);
        //store the reference to all the required views
        mWordTextview = view.findViewById(R.id.word);
        mDefinitionTextview = view.findViewById(R.id.meaning);
        mBookmarkTogglebutton = view.findViewById(R.id.toggleButton);
        Bundle bundle = getArguments();
       String word;
        if (bundle != null) {
            word = bundle.getString("word_key", "nan");
            Log.d("ARGUMENTWORD", word);
            Cursor cursor = queryDatabase(word);
            displayWords(cursor);

        }
            //restore to previous state if the data is present
        if (savedInstanceState != null)
        {
            wordText = savedInstanceState.getString("wordTextKey");
            wordDef = savedInstanceState.getString("wordDefKey");
            wordBookmark = savedInstanceState.getBoolean("wordBookmarkKey");
            mWordTextview.setText(wordText);
            mDefinitionTextview.setText(Html.fromHtml(wordDef));
            mBookmarkTogglebutton.setChecked(wordBookmark);
        }
        //set toggle listener for the toggle button, the bookmarkbutton
        mBookmarkTogglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                updateData(isChecked);

            }
        });

     return  view;
    }
    //update the bookmark column on  change in the database.
    private void updateData(boolean isChecked) {

        DictionaryHelper2 dictHelper = new DictionaryHelper2(getContext());
        SQLiteDatabase db = dictHelper.getReadableDatabase();
        //db.rawQuery(rawStatement, new String[]{queryText.toLowerCase()});
        ContentValues values = new ContentValues();
        values.put(DictionaryContract.Words.COLUMN_ISBOOKMARKED,isChecked);
        db.update(DictionaryContract.Words.TABLE_NAME,values,"word = ?",new String[]{wordText});
        dictHelper.close();
    }
    //query the database with the user entered query string.
    private Cursor queryDatabase(String query){
        try {
            DictionaryHelper2 dictHelper = new DictionaryHelper2(getContext());
            SQLiteDatabase db = dictHelper.getReadableDatabase();
            String rawStatement = "SELECT " + DictionaryContract.Words.COLUMN_WORD + "," +DictionaryContract.Words.COLUMN_TYPE+ ","  + DictionaryContract.Words.COLUMN_DEFINITION + ","  + DictionaryContract.Words.COLUMN_ISBOOKMARKED +
                    " FROM " + DictionaryContract.Words.TABLE_NAME + " WHERE word ==" +
                    " ?;";
            Cursor cursor = db.rawQuery(rawStatement, new String[]{query.toLowerCase()});
            Log.d(TAG, "queryDatabase: "+cursor.getCount());
            dictHelper.close();
            return  cursor;
            //displayWords(cursor);

        }
        catch (SQLException e)
        {
            Log.d(TAG,"Error with database operation");
        }
        return null;
    }
    //display the data from the cursor obtained by the query..
    private void displayWords(Cursor cursor)
    {
        String colorText = "<font color = \"%s\">%s</font>. ";
        ArrayList<Word> words = Utility.getWordsFromCursor(cursor);
        if(words == null || words.isEmpty() )
            return;
        wordText = words.get(0).name;
        wordBookmark = words.get(0).isBookmarked;
        mBookmarkTogglebutton.setChecked(wordBookmark);
        mWordTextview.setText(wordText);
        wordDef = "";
        Log.d(TAG, "displayWords: "+words.size());
        mDefinitionTextview.setText("");
        for (Word word:words) {

            StringBuilder builder = new StringBuilder();
            builder.append(String.format(colorText,"#BBBFC3",word.type.toLowerCase()));
            builder.append(String.format(colorText,"a4a5a6",word.definition).toLowerCase());
            builder.append("<br></br>");
            builder.append("<br></br>");
            String wordDefTemp = builder.toString();
            wordDef += wordDefTemp;
            mDefinitionTextview.append(Html.fromHtml(wordDefTemp));

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchViewItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setIconifiedByDefault(true);
        //set on query text changed listener which queries the database when query is submitted by the user..
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty())
                {
                    queryText = query;
                    Cursor cursor = queryDatabase(query);
                    if (cursor.getCount()>0){
                        ((OnQueryByFragment)getActivity()).updateWords(
                                new Word(query,"","",true)
                        );}
                    displayWords(cursor);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    //save off the word in bundle..
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("wordTextKey",wordText);
        outState.putString("wordDefKey",wordDef);
        outState.putBoolean("wordBookmarkKey",wordBookmark);
        super.onSaveInstanceState(outState);

    }
}
