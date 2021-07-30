package com.example.moody.models;

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
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_OBJ_ID = "objectId";

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
    public String getId() {
        return getString(KEY_OBJ_ID);
    }
    public void setId(String objectId) {
        put(KEY_OBJ_ID, objectId);
    }
    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }
        return "";
    }
}
