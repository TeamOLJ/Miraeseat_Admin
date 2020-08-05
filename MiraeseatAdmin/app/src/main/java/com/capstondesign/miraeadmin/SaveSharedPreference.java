package com.capstondesign.miraeadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    //static final String PREF_USER_NAME = "username";
    static final String SAVED_EMAIL = null;
    static final String IS_EMAIL_SAVED = "AutoLogin";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setSavedEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SAVED_EMAIL, email);
        editor.apply();
    }

    public static String getSavedEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(SAVED_EMAIL, "");
    }

    public static void setIsEmailSaved(Context ctx, boolean isEmailSaved) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(IS_EMAIL_SAVED, isEmailSaved);
        editor.apply();
    }

    public static boolean getIsEmailSaved(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(IS_EMAIL_SAVED, false);
    }
}