package com.apps.anilgr.simpledictionary.data;

/**
 * Created by anilgr on 5/2/18.
 */

public class Word {
    enum WordType{
       VERB, ADJ, PREP, ADV, OTHER, NOUN

    }
    public String name;
    public String definition;
    public String type;
    public boolean isBookmarked = false;
    public Word(String name, String type, String definition, boolean isBookmarked)
    {
        this.name = name;
        this.definition = definition;
        this.type = type;
        this.isBookmarked = isBookmarked;
    }

}
