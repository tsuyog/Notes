package com.tsuyognote.tsuyogbasnet.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tsuyogbasnet on 8/31/15.
 */
public class NoteItems {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public static NoteItems getNote(){
        Locale locale = new Locale("en_US");
        locale.setDefault(locale);
        String pattern = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String key = formatter.format(new Date());
        NoteItems noteItems = new NoteItems();
        noteItems.setKey(key);
        noteItems.setValue(" ");
        return noteItems;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
