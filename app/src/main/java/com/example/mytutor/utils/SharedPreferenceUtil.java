package com.example.mytutor.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytutor.model.Account;

public class SharedPreferenceUtil {

    public static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("data_app", Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setBoolean(String key, Boolean data) {
        sharedPreferences.edit().putBoolean(key, data).apply();
    }

    public static Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @SuppressLint("CommitPrefEdits")
    public static void setString(String key, String data) {
        sharedPreferences.edit().putString(key, data).apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    @SuppressLint("CommitPrefEdits")
    public static void setInteger(String key, Integer data) {
        sharedPreferences.edit().putInt(key, data).apply();
    }

    public static Integer getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static void saveLoggedAccountInfo(Account loggedAccount, String password, Boolean autoLogin) {
        setString(AppConstants.ACCESS_TOKEN, loggedAccount.getAccessToken());
        setString(AppConstants.ACCOUNT_ID, loggedAccount.getId());
        setString(AppConstants.USERNAME, loggedAccount.getUsername());
        setString(AppConstants.PASSWORD, password);
        setString(AppConstants.NAME, loggedAccount.getName());
        setString(AppConstants.GENDER, FunctionUtils.getGenderName(loggedAccount.getGender()));
        setString(AppConstants.DOB, loggedAccount.getDob());
        setString(AppConstants.PHONE, loggedAccount.getPhone());
        setString(AppConstants.EMAIL, loggedAccount.getEmail());
        setInteger(AppConstants.MONEY, loggedAccount.getMoney().intValue());
        setInteger(AppConstants.ROLE, loggedAccount.getRole());
        setString(AppConstants.ROLE_NAME, FunctionUtils.getRoleName(loggedAccount.getRole()));
        setString(AppConstants.CREATED_AT, FunctionUtils.sdf.format(loggedAccount.getCreatedAt()));
        setBoolean(AppConstants.REMEMBER, autoLogin);
    }

    public static void updateLoggedAccountInfo(Account loggedAccount) {
        setString(AppConstants.NAME, loggedAccount.getName());
        setString(AppConstants.GENDER, FunctionUtils.getGenderName(loggedAccount.getGender()));
        setString(AppConstants.DOB, loggedAccount.getDob());
        setString(AppConstants.PHONE, loggedAccount.getPhone());
    }

    public static void clearLoggedAccountInfo() {
        setString(AppConstants.ACCESS_TOKEN, "");
        setString(AppConstants.ACCOUNT_ID, "");
        setString(AppConstants.NAME, "");
        setString(AppConstants.GENDER, "");
        setString(AppConstants.DOB, "");
        setString(AppConstants.PHONE, "");
        setString(AppConstants.EMAIL, "");
        setInteger(AppConstants.MONEY, 0);
        setInteger(AppConstants.ROLE, 0);
        setString(AppConstants.ROLE_NAME, "");
        setString(AppConstants.CREATED_AT, "");
    }
}
