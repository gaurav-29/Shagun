package com.oceanmtech.shagun.DashboardModule.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    public static SharedPreferences ShagunSharedPreference;

    public static void putString(String key, String value) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ShagunSharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return ShagunSharedPreference.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ShagunSharedPreference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return ShagunSharedPreference.getInt(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ShagunSharedPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        ShagunSharedPreference = MyApplication.getAppContext().getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean string = ShagunSharedPreference.getBoolean(key, defaultValue);
        return string;
    }

    public static boolean contains(String key) {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (ShagunSharedPreference.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

//    public static void remove(String key) {
//        IFlipAllSharedPreference = MyApplication.getAppContext()
//                .getSharedPreferences(com.amin.iflipall.Utils.Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = IFlipAllSharedPreference.edit();
//        editor.remove(key);
//        editor.commit();
//    }

    public static void clearPreference() {
        ShagunSharedPreference = MyApplication.getAppContext()
                .getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        ShagunSharedPreference.edit().clear().apply();
    }
}
