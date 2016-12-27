package com.arplus.android.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class SessionManagement {

    private static final String PREF_NAME = "ARPlusPref";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public SessionManagement(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();

    }

//    exp
//    public String getDummy(){
//        String dummy = "";
//        if(preferences.getString("DUMMY","")!=null){
//            dummy = preferences.getString("DUMMY","");
//        }
//        return dummy;
//    }
//
//    public void setDummy(String string) {
//        editor.putString("DUMMY", string);
//        editor.commit();
//    }
}
