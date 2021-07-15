package com.example.moody;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Entry")
public class Entry extends ParseObject {

    public static final String KEY_CAPTION = "caption";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_EMOTION = "emotion";

    //define getters and setters for each key using getter defined in PArseObject class
    public String getCaption() {
        return getString(KEY_CAPTION);
    }
    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }
    public ParseUser getAuthor() {
        return getParseUser(KEY_AUTHOR);
    }
    public void setAuthor(ParseUser user) {
        put(KEY_AUTHOR, user);
    }
    public String getEmotion() {return getString(KEY_EMOTION);}
    public void setEmotion(String emotion) { put(KEY_EMOTION, emotion);}

}
