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
    public static final String KEY_LIKES = "likes";
    public static final String KEY_EXCLAIMS = "exclaims";

//        public enum Mood {
//        HAPPY("happy", 0),
//        SAD("sad",1),
//        ANXIOUS("anxious", 0),
//        ZEN("zen", 0),
//        ANGRY("angry", 0),
//        EXCITED("excited", 0),;
//
//        private String stringValue;
//        private int intValue;
//        private Mood(String toString, int value) {
//            stringValue = toString;
//            intValue = value;
//        }
//        @Override
//        public String toString() {
//            return stringValue;
//        }
//    }

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
    public int getLikes() {
        return getInt(KEY_LIKES);
    }
    public void setLikes(int likes) {
        put(KEY_LIKES, likes);
    }
    public int getExclaims() {
        return getInt(KEY_EXCLAIMS);
    }
    public void setExclaims(int exclaims) {
        put(KEY_EXCLAIMS, exclaims);
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
