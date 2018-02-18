package com.apps.anilgr.simpledictionary;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.anilgr.simpledictionary.data.Word;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements WordFragment.OnQueryByFragment {
    public static String TAG  = "DANILGR";
    private ArrayList<Word> list;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = Utility.getBookmarks(getApplication());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new WordSlidePagerAdapter(getSupportFragmentManager(),list));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dictionary_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        //start the settings activity when setting menu is clicked..
    if(item.getItemId() == R.id.action_settings) {
        intent = new Intent(getApplicationContext(), DictionaryPreferenceActivity.class);
        startActivity(intent);
    }
    if (item.getItemId() == R.id.action_bookmark)
    {
        intent = new Intent(getApplication(), BookmarkListActivity.class);
        startActivity(intent);
    }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateWords(Word word) {
       // Word tmp = list.get(mViewPager.getCurrentItem());
        list.add(mViewPager.getCurrentItem(),word);
       // list.add(tmp);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    private class WordSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Word> list;
        public WordSlidePagerAdapter(FragmentManager fm,ArrayList<Word> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fg = new WordFragment();
            Bundle bundle = new Bundle();
            bundle.putString("word_key",list.get(position).name);
            fg.setArguments(bundle);
            return fg;
        }

        @Override
        public int getCount() {
            return list.size();
        }


    }

}
