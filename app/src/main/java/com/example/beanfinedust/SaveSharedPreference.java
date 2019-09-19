package com.example.beanfinedust;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_EMAIL = "Email";
    static final String PREF_PASS = "password";

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserData(Context ctx, String email, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PASS, password);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static String getUserData(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "")+","+getSharedPreferences(ctx).getString(PREF_PASS, "");
    }

    // 로그아웃
    public static void clearUserData(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
