package com.apps.anilgr.simpledictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps.anilgr.simpledictionary.data.Word;

import java.util.ArrayList;


/**
 * Created by anilgr on 12/2/18.
 */

public class BookmarkAdapter extends ArrayAdapter<Word> {
    ArrayList<Word> words;
    Context context;
    public BookmarkAdapter(@NonNull Context context, @NonNull ArrayList<Word> list) {
        super(context, 0, list);
        this.words = list;
        this.context  = context;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
       {
          convertView = LayoutInflater.from(context).inflate(R.layout.item_bookmark,parent,false);
       }

        TextView text = (TextView) convertView.findViewById(R.id.item_text);
        text.setText(getItem(position).name);
        return convertView;
    }
}
